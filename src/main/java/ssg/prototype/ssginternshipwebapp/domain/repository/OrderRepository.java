package ssg.prototype.ssginternshipwebapp.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ssg.prototype.ssginternshipwebapp.domain.entity.Jumun;
import ssg.prototype.ssginternshipwebapp.domain.entity.JumunId;

@Repository
public interface OrderRepository extends JpaRepository<Jumun, JumunId>{
	public List<Jumun> findByCustomerId(Long customerId);
	public List<Jumun> findByOrderId(int orderId);
	public List<Jumun> findByOrderId0(int orderId0);
}
