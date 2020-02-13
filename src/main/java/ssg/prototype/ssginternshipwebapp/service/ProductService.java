package ssg.prototype.ssginternshipwebapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import ssg.prototype.ssginternshipwebapp.domain.entity.Product;
import ssg.prototype.ssginternshipwebapp.domain.repository.ProductRepository;

@Service
public class ProductService {
	private final ProductRepository productRepository;
	
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	public List<Product> findProductsById(Set<String> ids) {
		List<Product> checkedProducts = new ArrayList<Product>();
		for(String id : ids) {
			System.out.println(id);
			Optional<Product> op = productRepository.findById(Long.parseLong(id));		
			if(op.isPresent()) {
				Product pro = op.get();
				pro.setStock(pro.getStock()-1);
				checkedProducts.add(op.get());
			}
		}
		return checkedProducts;
	}
}
