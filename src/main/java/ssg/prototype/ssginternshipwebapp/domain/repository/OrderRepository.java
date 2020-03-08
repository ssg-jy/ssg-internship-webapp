package ssg.prototype.ssginternshipwebapp.domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ssg.prototype.ssginternshipwebapp.domain.entity.Jumun;
import ssg.prototype.ssginternshipwebapp.domain.entity.JumunId;

@Repository
public interface OrderRepository extends CrudRepository<Jumun, JumunId>{
	public List<Jumun> findByCustomerId(Long customerId);
	public List<Jumun> findByOrderId(int orderId);
	public List<Jumun> findByOrderId0(int orderId0);
//	
//	@Query("SELECT o FROM JUMUN o WHERE o.customerId = ?1 AND o.CODE = ?2")
//	public List<Jumun> findByCidAndCode(Long cid, int code);
	public List<Jumun> findByCustomerIdAndCode(Long cid, int code);
	
	public List<Jumun> findAllByOrderByOrderId0();
}
