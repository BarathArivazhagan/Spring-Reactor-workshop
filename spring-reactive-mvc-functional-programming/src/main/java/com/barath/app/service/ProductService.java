package com.barath.app.service;


import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.barath.app.entity.Product;
import com.barath.app.exception.ProductExistsException;
import com.barath.app.exception.ProductNotExistsException;
import com.barath.app.repository.ProductRepository;

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
	
	
	public Mono<Product> saveProduct(Product product){
		
		
		if(isExists(product.getProductId())){
			if(log.isInfoEnabled()){
				log.info("Product "+product.toString()+" getting saved");
			}
			return Mono.just(productRepository.save(product));
		
		}else{
			throw new ProductExistsException("Product with product Id "+product.getProductId()+" already exists");
		}
		
	}
	
	public Mono<Product> getProduct(Long productId){
		
		if(log.isInfoEnabled()){
			log.info("Getting Product with product id "+productId);
		}
		return Mono.justOrEmpty(productRepository.findById(productId));
	}

	public Mono<Product> getProductByName(String productName){
		
		if(log.isInfoEnabled()){
			log.info("Getting Product with product name {}",productName);
		}
		return Mono.justOrEmpty(productRepository.findByProductName(productName));
	
	}


	public Mono<Product> updateProduct(Product product){
		
		if(isExists(product.getProductId())){
			return Mono.justOrEmpty(productRepository.save(product));
		}else{
			throw new ProductNotExistsException("Product doesnt exists so it cannot be updated");
		}
		
	}
	
	public void deleteProduct(Long productId){
		
		if(isExists(productId)){			
			productRepository.deleteById(productId);
			
		}else{
			throw new ProductNotExistsException("Product does not exists ");
		}
		
	}
	
	public Mono<Long> getCount(){
		
		return Mono.just(productRepository.count());
	}
	
	public Flux<Product> getProducts(){
		
		return Flux.fromIterable(productRepository.findAll());
	}
	
	public boolean isExists(Long productId){
		
		return productRepository.existsById(productId);
	}
	
	

}
