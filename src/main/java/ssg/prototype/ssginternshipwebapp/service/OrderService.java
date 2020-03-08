package ssg.prototype.ssginternshipwebapp.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import ssg.prototype.ssginternshipwebapp.OrdCode;
import ssg.prototype.ssginternshipwebapp.OrdStat;
import ssg.prototype.ssginternshipwebapp.domain.entity.Jumun;
import ssg.prototype.ssginternshipwebapp.domain.repository.OrderRepository;

@Service
public class OrderService {
	private final OrderRepository orderRepository;
	
	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	public void saveOrder(Long customerId, int orderId, int ordCode, int orderId0) {
		SimpleDateFormat format = new SimpleDateFormat("E MMM dd HH:mm:ss");
		String time = format.format(new Date());
		int ordStat = OrdStat.ORD_COMPLETE;
		if(ordCode == OrdCode.RETURN)
			ordStat = OrdStat.RET_REQ;
		orderRepository.save(new Jumun(customerId, orderId, time, ordStat, ordCode, orderId0));
	}
	
}
