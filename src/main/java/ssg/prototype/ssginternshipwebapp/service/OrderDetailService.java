package ssg.prototype.ssginternshipwebapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ssg.prototype.ssginternshipwebapp.domain.entity.JumunDetail;
import ssg.prototype.ssginternshipwebapp.domain.entity.Product;
import ssg.prototype.ssginternshipwebapp.domain.repository.OrderDetailRepository;

@Service
public class OrderDetailService {
	private final OrderDetailRepository orderDetailRepository;
	
	public OrderDetailService(OrderDetailRepository orderDetailRepository) {
		this.orderDetailRepository = orderDetailRepository;
	}
	
	public List<JumunDetail> saveOrder(int orderId, Map<String, String> qtys) {
		
		List<JumunDetail> orderDetails = new ArrayList<JumunDetail>();
		for(String pId : qtys.keySet()) {
			Long pid = Long.parseLong(pId);
			int qty = Integer.parseInt(qtys.get(pId));
			if(qty > 0) orderDetails.add(new JumunDetail(orderId, pid, qty));
		}
		orderDetailRepository.saveAll(orderDetails);
		return orderDetails;
	}
	
	public List<JumunDetail> showOrder(int oid) {
		return orderDetailRepository.findByOrderId(oid);
	}
}
