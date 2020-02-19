package ssg.prototype.ssginternshipwebapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ssg.prototype.ssginternshipwebapp.ItemCode;
import ssg.prototype.ssginternshipwebapp.OrdCode;
import ssg.prototype.ssginternshipwebapp.OrdStat;
import ssg.prototype.ssginternshipwebapp.Status;
import ssg.prototype.ssginternshipwebapp.domain.entity.Customer;
import ssg.prototype.ssginternshipwebapp.domain.entity.Jumun;
import ssg.prototype.ssginternshipwebapp.domain.entity.JumunDetail;
import ssg.prototype.ssginternshipwebapp.domain.entity.Product;
import ssg.prototype.ssginternshipwebapp.domain.entity.Variable;
import ssg.prototype.ssginternshipwebapp.domain.repository.CustomerRepository;
import ssg.prototype.ssginternshipwebapp.domain.repository.OrderRepository;
import ssg.prototype.ssginternshipwebapp.domain.repository.ProductRepository;
import ssg.prototype.ssginternshipwebapp.domain.repository.VariableRepository;
import ssg.prototype.ssginternshipwebapp.service.OrderDetailService;
import ssg.prototype.ssginternshipwebapp.service.OrderService;
import ssg.prototype.ssginternshipwebapp.service.ProductService;

@Controller
@EnableAutoConfiguration
@RequestMapping(value = "/order")
public class OrderController {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	VariableRepository variableRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderDetailService orderDetailService;

	@GetMapping({"/list/{name}"})
	public String showList(@PathVariable("name") String name, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String cname = (String) session.getAttribute("cname");
		Long cid = (Long) session.getAttribute("cid");
		if(cname == null) {
			return "redirect:/";
		} else {
			if(!name.equals(cname)) {
				return "redirect:/"; // 에러 페이지 출력해야!!!
			}
		}
		
		/******** 많이 아쉬운 부분!!! 이런건 처음부터 db에서 sql로 걸러줘야 하는데!!********/
		List<Jumun> orders = orderRepository.findByCustomerId(cid);
		orders.removeIf(o -> o.getCode() != OrdCode.ORDER);
		/**************/
		model.addAttribute("stat_string", OrdStat.stat_string);
		model.addAttribute("orders", orders);
		return "/order/list";
	}
	
	@GetMapping("/detail/{oid0}")
	public String showDetail(@PathVariable("oid0") int oid0, Model model, HttpSession session) {
		/*
		* 여기서 oid로 db 조회해서 사용자 아이디와 session의 cid 가 동일한지 확인해야한다!!
		*/
		List<Jumun> ord = orderRepository.findByOrderId0(oid0);
		System.out.println("원주문번호로 찾은 주문 개수: "+ord.size());
		List<Status> statuses = new ArrayList<Status>();
		statuses.add(new Status(OrdStat.stat_string[0], false));
		statuses.add(new Status(OrdStat.stat_string[1], false));
		statuses.add(new Status(OrdStat.stat_string[2], false));
		statuses.get(ord.get(0).getStatus()).setNow(true);
		
		boolean delivered = false;
		if(ord.get(0).getStatus() == OrdStat.DLV_COMPLETE) {
			delivered = true;
		}
		
		List<JumunDetail> canceled = new ArrayList<JumunDetail>();
		for(int i=1; i<ord.size(); i++) {
			if(ord.get(i).getCode() == OrdCode.PART_CANCEL) {
				System.out.println("part_cancel order");
				canceled.addAll(orderDetailService.showOrder(ord.get(i).getOrderId()));
			}
		}
		System.out.println("canceled size: "+canceled.size());
		List<Product> canceledProducts = productService.findProductsById(canceled);
		
		List<JumunDetail> orderDetails = orderDetailService.showOrder(ord.get(0).getOrderId());
		List<Product> products = productService.findProductsById(orderDetails);
		
		int total = 0;
		for(int i=0; i<products.size(); i++) {
			total += orderDetails.get(i).getQty() * products.get(i).getPrice();
		}
		
		model.addAttribute("delivered", delivered);
		model.addAttribute("canceled", canceled);
		model.addAttribute("cldPducts", canceledProducts);
		model.addAttribute("orderDate", ord.get(0).getOrderedDate());
		model.addAttribute("statuses", statuses);
		model.addAttribute("orderDetails", orderDetails);
		model.addAttribute("products", products);
		model.addAttribute("name", session.getAttribute("cname"));
		model.addAttribute("total", total);
		model.addAttribute("ItemCode", new ItemCode());
		model.addAttribute("orderId", oid0);
		return "/order/detail";
	}
	
	// 취소 주문은 주문을 새로 생성하고 (원주문번호**) 주문 상세 정보들을 수정해야.
	@PostMapping("/cancel/{name}")
	public String cancelOrder(@PathVariable("name") String name,
			@RequestParam(value="checked") String[] checked,
			@RequestParam(value="orderId0") String orderId0_,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		Long cid = (Long) session.getAttribute("cid");
		
		/******* 이 부분 따로 빼야함 ********/
		Optional<Variable> ocount_ = variableRepository.findById("ocount");
		Variable ocount = ocount_.get(); 
		int newOrderId = ocount.getValue();
		ocount.setValue(newOrderId+1);
		variableRepository.save(ocount);
		/***********/
		
		int orderId0 = Integer.parseInt(orderId0_);
		orderService.saveOrder(cid, newOrderId, OrdCode.PART_CANCEL, orderId0);
		
		orderDetailService.cancelOrder(orderId0, newOrderId, checked);
		return "redirect:/order/detail/"+orderId0;
	}
	
	@PostMapping("/delivered/{oid}")
	public String deliveredOrder(@PathVariable("oid") int oid) {
		List<Jumun> ord = orderRepository.findByOrderId(oid);
		Jumun order = ord.get(0);
		order.setStatus(OrdStat.DLV_COMPLETE);
		orderRepository.save(order);
		return "redirect:/order/detail/"+oid;
	}
}
