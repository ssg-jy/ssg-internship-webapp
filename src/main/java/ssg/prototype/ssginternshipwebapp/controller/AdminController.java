package ssg.prototype.ssginternshipwebapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ssg.prototype.ssginternshipwebapp.OrdCode;
import ssg.prototype.ssginternshipwebapp.OrdStat;
import ssg.prototype.ssginternshipwebapp.domain.entity.Customer;
import ssg.prototype.ssginternshipwebapp.domain.entity.Jumun;
import ssg.prototype.ssginternshipwebapp.domain.repository.CustomerRepository;
import ssg.prototype.ssginternshipwebapp.domain.repository.OrderRepository;


@Controller
@EnableAutoConfiguration
@RequestMapping(value="/admin")
public class AdminController {
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@GetMapping("/")
	public String home(Model model) {
		System.out.println("admin page");
		
		Iterable<Jumun> orders = orderRepository.findAllByOrderByOrderId0();
		
		Iterable<Customer> customers = customerRepository.findAll();

		Map<Long, String> cmIdName = new HashMap<Long, String>();
		for(Customer c : customers) {
			cmIdName.put(c.getId(), c.getName());
		}
		
		
		model.addAttribute("codeString", OrdCode.code_string);
		model.addAttribute("statString", OrdStat.stat_string);
		model.addAttribute("customers", cmIdName);
		model.addAttribute("orders", orders);
		
		return "/admin/admin";
	}
}
