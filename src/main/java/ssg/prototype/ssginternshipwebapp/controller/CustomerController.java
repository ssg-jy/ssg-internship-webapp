package ssg.prototype.ssginternshipwebapp.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ssg.prototype.ssginternshipwebapp.domain.entity.Customer;
//import com.example.jpa.entity.Product;
import ssg.prototype.ssginternshipwebapp.domain.repository.CustomerRepository;

@Controller
@EnableAutoConfiguration
@RequestMapping
public class CustomerController {
	
	@Autowired
	CustomerRepository customerRepository;
	
	@GetMapping("/")
	public String home(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Long cid = (Long)session.getAttribute("cid");
		if(cid != null) {
			Optional<Customer> customer = customerRepository.findById(cid);
			if(customer.isPresent()) {
				return "redirect:/product/"+customer.get().getName();
			}
		}
			
		return "/customer/signin";
	}
	
	// 가입용 아직 안씀.
	@PostMapping("/join")
	public String createCustomer(@RequestParam Map<String,String> param, HttpServletRequest request){
		HttpSession session = request.getSession();
		
		String name = param.get("name");
		System.out.println(name);
		String password = param.get("password");
		System.out.println(password);
		
		Customer customer = Customer.builder().name(name).password(password).build();
		customer = customerRepository.save(customer);
		
		session.setAttribute("cid", customer.getId());
		session.setAttribute("cname", customer.getName());
		
		return "redirect:/product/"+name;
	}
	
	@PostMapping("/login")
	public String logIn(@RequestParam Map<String, String> param, HttpServletRequest request) {
		// 서비스로 보낸다. 서비스에서 리포지토리 조회해서 맞으면 세션에 id 저장해야.
		HttpSession session = request.getSession();
		Customer triedCustomer = customerRepository.findByName((String)param.get("name")).get(0);
		System.out.println(triedCustomer.getName()+" "+triedCustomer.getPassword());
		if(triedCustomer.getPassword().equals(param.get("password"))) {
			session.setAttribute("cid", triedCustomer.getId());
			session.setAttribute("cname", triedCustomer.getName());
			return "redirect:/product/"+triedCustomer.getName();
//			return "/customer/signin";
		}
		return "/customer/signin";
	}
	
	@GetMapping("/customer/{cid}")
	public String logIn(@PathVariable("cid") int cid) {
		return "redirect:/product/";
	}
}
