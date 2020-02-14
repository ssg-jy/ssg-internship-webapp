package ssg.prototype.ssginternshipwebapp.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ssg.prototype.ssginternshipwebapp.domain.entity.Customer;
import ssg.prototype.ssginternshipwebapp.domain.entity.JumunDetail;
import ssg.prototype.ssginternshipwebapp.domain.entity.Product;
import ssg.prototype.ssginternshipwebapp.domain.entity.Variable;
import ssg.prototype.ssginternshipwebapp.domain.repository.CustomerRepository;
import ssg.prototype.ssginternshipwebapp.domain.repository.ProductRepository;
import ssg.prototype.ssginternshipwebapp.domain.repository.VariableRepository;
import ssg.prototype.ssginternshipwebapp.service.OrderDetailService;
import ssg.prototype.ssginternshipwebapp.service.OrderService;
import ssg.prototype.ssginternshipwebapp.service.ProductService;

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

	@GetMapping({"","/","/{name}"})
	public String showProducts(@PathVariable("name") String name, Model model, HttpServletRequest request) {
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

		/* 
		 * qtys에서 수량이 0인 상품-수량 쌍 제거 
		 * 각 함수에서 계속 0이상인지 검사하지 않고 그냥 여기서 제거하고 넘기는 걸로!
		 */
		qtys.entrySet().removeIf(e -> e.getValue().equals("0"));
		
		
//		Object orderId_ = session.getAttribute("orderId");
		Optional<Variable> ocount_ = variableRepository.findById("ocount");
		int orderId = 1;
		if(ocount_.isPresent()) {
			Variable ocount = ocount_.get(); 
			orderId = ocount.getValue();
			ocount.setValue(orderId+1);
			variableRepository.save(ocount);
		}
		/*
		if(orderId_ == null) {
			session.setAttribute("orderId", 1);
		} else {
			orderId = (int)orderId_;
		}
		*/
		orderService.saveOrder(cid, orderId); // 세션에 저장된 customer key 로 해야.
		productService.updateQty(qtys);
		List<JumunDetail> orderDetails = orderDetailService.saveOrder(orderId, qtys);
		List<Product> products = productService.findProductsById(orderDetails);

//		session.setAttribute("orderId", orderId+1);
		
		// orderService를 만들어야 함!! // ordered에 넣어줘야 한다!!
//		model.addAttribute("ordered", ordered);
		model.addAttribute("orderDetails", orderDetails);
		model.addAttribute("products", products);
		return "/product/order";
	}
}
