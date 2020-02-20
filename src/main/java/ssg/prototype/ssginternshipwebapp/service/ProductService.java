package ssg.prototype.ssginternshipwebapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import ssg.prototype.ssginternshipwebapp.domain.entity.JumunDetail;
import ssg.prototype.ssginternshipwebapp.domain.entity.Product;
import ssg.prototype.ssginternshipwebapp.domain.repository.ProductRepository;

@Service
public class ProductService {
	private final ProductRepository productRepository;
	
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	public List<Product> findProductsById(Set<String> ids) {
		ArrayList<Long> pids = new ArrayList<Long>();
		for(String id : ids) {
			pids.add(Long.parseLong(id));
		}
		return productRepository.findAllById(pids);
	}
	
	public List<Product> findProductsById(List<JumunDetail> orderDetails) {
		ArrayList<Product> products = new ArrayList<Product>();
		for(JumunDetail detail : orderDetails) {
			Optional<Product> op = productRepository.findById(detail.getProductId());
			if(op.isPresent()) products.add(op.get());
		}
		return products;
	}
	
	// 제품 테이블의 재고 업데이트
	public void updateQty(Map<String, String> qtys, boolean flag) {
		Set<String> ids = qtys.keySet();
		for(String id : ids) {
			Optional<Product> op = productRepository.findById(Long.parseLong(id));
			if(op.isPresent()) {
				Product pro = op.get();
				if(!flag)
					pro.setStock(pro.getStock() - Integer.parseInt(qtys.get(id)));
				else
					pro.setStock(pro.getStock() + Integer.parseInt(qtys.get(id)));
				productRepository.save(pro);
//				System.out.println("재고 업데이트 "+op.get().getName()+" "+op.get().getStock());
			}
		}
	}
}
