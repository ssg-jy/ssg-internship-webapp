package ssg.prototype.ssginternshipwebapp.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ssg.prototype.ssginternshipwebapp.domain.entity.JumunDetail;
import ssg.prototype.ssginternshipwebapp.domain.entity.JumunDetailId;

@Repository
public interface OrderDetailRepository extends JpaRepository<JumunDetail, JumunDetailId>{
	public List<JumunDetail> findByProductId(Long productId);
	public List<JumunDetail> findByOrderId(int orderId);
}
