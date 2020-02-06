package ssg.prototype.ssginternshipwebapp.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ssg.prototype.ssginternshipwebapp.domain.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	public List<Customer> findByName(String name);
	public List<Customer> findByPassword(String password);
	//like검색도 가능
	public List<Customer> findByNameLike(String keyword);
}