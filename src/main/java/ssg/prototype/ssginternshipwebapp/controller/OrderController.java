package ssg.prototype.ssginternshipwebapp.controller;

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
		
		model.addAttribute("orders", orders);
		return "/order/list";
	}
	
	@GetMapping("/detail/{oid}")
	public String showDetail(@PathVariable("oid") int oid, Model model, HttpSession session) {
		List<JumunDetail> orderDetails = orderDetailService.showOrder(oid);
		List<Product> products = productService.findProductsById(orderDetails);
		model.addAttribute("orderDetails", orderDetails);
		model.addAttribute("products", products);
		model.addAttribute("name", session.getAttribute("cname"));
		return "/order/detail";
	}
	
}
