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

import ssg.prototype.ssginternshipwebapp.OrdStat;
import ssg.prototype.ssginternshipwebapp.Status;
import ssg.prototype.ssginternshipwebapp.domain.entity.Customer;
import ssg.prototype.ssginternshipwebapp.domain.entity.Jumun;
import ssg.prototype.ssginternshipwebapp.domain.entity.JumunDetail;
import ssg.prototype.ssginternshipwebapp.domain.entity.Product;
import ssg.prototype.ssginternshipwebapp.domain.repository.CustomerRepository;
import ssg.prototype.ssginternshipwebapp.domain.repository.OrderRepository;
import ssg.prototype.ssginternshipwebapp.domain.repository.ProductRepository;
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
		List<Jumun> orders = orderRepository.findByCustomerId(cid);
		
		model.addAttribute("stat_string", OrdStat.stat_string);
		model.addAttribute("orders", orders);
		return "/order/list";
	}
	
	@GetMapping("/detail/{oid}")
	public String showDetail(@PathVariable("oid") int oid, Model model, HttpSession session) {
		/*
		* 여기서 oid로 db 조회해서 사용자 아이디와 session의 cid 가 동일한지 확인해야한다!!
		*/
		List<Jumun> ord = orderRepository.findByOrderId(oid);
		List<Status> statuses = new ArrayList<Status>();
		statuses.add(new Status(OrdStat.stat_string[0], false));
		statuses.add(new Status(OrdStat.stat_string[1], false));
		statuses.add(new Status(OrdStat.stat_string[2], false));
		statuses.get(ord.get(0).getStatus()).setNow(true);
		
		List<JumunDetail> orderDetails = orderDetailService.showOrder(oid);
		List<Product> products = productService.findProductsById(orderDetails);
		
		model.addAttribute("orderDate", ord.get(0).getOrderedDate());
		model.addAttribute("statuses", statuses);
		model.addAttribute("orderDetails", orderDetails);
		model.addAttribute("products", products);
		model.addAttribute("name", session.getAttribute("cname"));
		return "/order/detail";
	}
	
}
