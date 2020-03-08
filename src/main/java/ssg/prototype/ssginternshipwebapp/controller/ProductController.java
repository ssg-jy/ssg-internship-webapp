package ssg.prototype.ssginternshipwebapp.controller;

import java.util.List;
import java.util.Map;
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

import ssg.prototype.ssginternshipwebapp.OrdCode;
import ssg.prototype.ssginternshipwebapp.domain.entity.Customer;
import ssg.prototype.ssginternshipwebapp.domain.entity.Product;
import ssg.prototype.ssginternshipwebapp.domain.repository.CustomerRepository;
import ssg.prototype.ssginternshipwebapp.domain.repository.ProductRepository;
import ssg.prototype.ssginternshipwebapp.domain.repository.VariableRepository;
import ssg.prototype.ssginternshipwebapp.service.OrderDetailService;
import ssg.prototype.ssginternshipwebapp.service.OrderService;
import ssg.prototype.ssginternshipwebapp.service.ProductService;
import ssg.prototype.ssginternshipwebapp.service.VariableService;

@Controller
@EnableAutoConfiguration
@RequestMapping(value = "/product")
public class ProductController {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	VariableRepository variableRepository;

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderDetailService orderDetailService;

	@Autowired
	VariableService variableService;	

	@GetMapping({"","/","/{name}"})
	public String showProducts(@PathVariable("name") String name, 
			Model model, HttpServletRequest request, 
			@RequestParam("orderId") Optional<Integer> orderId) {
		HttpSession session = request.getSession();
		String cname = (String) session.getAttribute("cname");
		if(cname == null) {
			return "redirect:/";
		} else {
			if(!name.equals(cname)) {
				return "redirect:/"; // 에러 페이지 출력해야!!!
			}
		}
		List<Product> lp = productRepository.findAll();
//		return lp.toString();
//		return lp.get(0).getName();
		model.addAttribute("name", name);
		model.addAttribute("productList", lp);
		int oid = -1;
		String ordText = "주문하기";
		if(orderId.isPresent()) {
			oid = orderId.get();
			ordText = "주문더하기";
		}
		model.addAttribute("orderId", oid);
		model.addAttribute("ordText", ordText);
		return "/product/list";
	}
	
	@PostMapping("/order/{name}")
	public String orderProducts(@PathVariable("name") String name, @RequestParam Map<String, String> qtys, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Long cid = (Long) session.getAttribute("cid");
		if(cid == null) {
			return "redirect:/";
		} else {
			Optional<Customer> customer = customerRepository.findById(cid);
			if(!customer.isPresent() || !name.equals(customer.get().getName())) {
				return "redirect:/"; // 에러 페이지 출력해야!!!
			}
		}
		
		String orderId_ = qtys.remove("orderId");
		/* 
		 * qtys에서 수량이 0인 상품-수량 쌍 제거 
		 * 각 함수에서 계속 0이상인지 검사하지 않고 그냥 여기서 제거하고 넘기는 걸로!
		 */
		qtys.entrySet().removeIf(e -> e.getValue().equals("0"));
		
		/**************** 여기 안넣고 밑에 넣으면 재고 업데이트가 안 됨..!!!! 나중에 알아봐야한다!!! ****************/
		productService.updateQty(qtys, false); // 제품 목록 재고 업데이트
		
//		Object orderId_ = session.getAttribute("orderId");
		int orderId = 1;
		
		if(!orderId_.equals("-1")) { // 주문더하기일 경우!
			orderId = Integer.parseInt(orderId_);
			orderDetailService.addOrder(orderId, qtys);
		} else { // 신규주문일 경우
//			if(!ocount_.isEmpty()) { // 항상 있음. 아예 시작전에 DB에 저장해놨음!(초기값: 1) = 없어도 되는 조건!!!
			orderId = variableService.newOid();
			
			// 신규주문인 경우 order를 새로 만든다.
			orderService.saveOrder(cid, orderId, OrdCode.ORDER, orderId); // 세션에 저장된 customer key 로 해야.
			
			// 신규주문인 경우에 상세주문에 저장
			orderDetailService.saveOrder(orderId, qtys);
//			}
		}
		
		return "redirect:/order/detail/"+orderId;
	}
}
