package com.barath.app;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ProductService {
	
	private ProductRepository productRepository;
	
	
	public ProductService(ProductRepository productRepository){		
		
		this.productRepository=productRepository;
		Assert.notNull(productRepository, "product repository cannot be null");
	}
	
	
	public void saveProduct(Product product){
		
		if(log.isInfoEnabled()){
			log.info("Product "+product.toString()+" getting saved");
		}
		productRepository.save(product);
	}
	
	public Mono<Product> getProduct(Long productId){
		
		if(log.isInfoEnabled()){
			log.info("Getting Product with product id "+productId);
		}
		return productRepository.findOne(productId);
	}

	public void getProductByName(String productName){
		
		if(log.isInfoEnabled()){
			log.info("Getting Product with product name "+productName);
		}
	
	}


	public void updateProduct(Product product){
		productRepository.save(product);
	}
	
	public void deleteProduct(Long productId){
		//productRepository.delete(productId);
	}
	
	public Mono<Long> getCount(){
		return productRepository.count();
	}
	
	public Flux<Product> getProducts(){
		return productRepository.findAll();
	}

}
