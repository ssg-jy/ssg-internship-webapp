package ssg.prototype.ssginternshipwebapp.service;

import java.util.ArrayList;
import java.util.List;

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
	
	public void saveOrder(int orderId, List<Product> ordered) {
		
		List<JumunDetail> orderDetails = new ArrayList<JumunDetail>();
		for(Product product : ordered) {
			orderDetails.add(new JumunDetail(orderId, product.getId()));
		}
		orderDetailRepository.saveAll(orderDetails);
	}
	
	public List<JumunDetail> showOrder(int oid) {
		return orderDetailRepository.findByOrderId(oid);
	}
}
