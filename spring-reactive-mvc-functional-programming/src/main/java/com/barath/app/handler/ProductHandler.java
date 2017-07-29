package com.barath.app.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.barath.app.entity.Product;
import com.barath.app.service.ProductService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ProductHandler {
	
	
	@Autowired
	private ProductService productService;	

	
	public Mono<ServerResponse> saveProduct(ServerRequest request){
		
		Mono<ServerResponse> response=null;
		Mono<Product> product=	request.bodyToMono(Product.class);
		if(log.isInfoEnabled()){
			log.info("Product {} getting saved",product.toString());
		}
		Mono<Product> savedProduct= productService.saveProduct(product.block());
		
		if( savedProduct !=null && savedProduct.block() !=null){
			response= ServerResponse.ok().body(BodyInserters.fromObject(savedProduct));
		}else{
			response=ServerResponse.notFound().build();
		}
		return response;
	}
	
	public Mono<ServerResponse> getProduct(ServerRequest request){
		
		Long productId=Long.valueOf(request.pathVariable("id"));
		if(productId != null){
			Mono<Product> product=productService.getProduct(productId);
			 
			 return ServerResponse.ok().body(BodyInserters.fromObject(product));
		}
		return ServerResponse.notFound().build();
	}

	public void getProductByName(String productName){
		
		if(log.isInfoEnabled()){
			log.info("Getting Product with product name "+productName);
		}
	
	}


//	public void updateProduct(Product product){
//		productService.save(product);
//	}
//	
//	public void deleteProduct(Long productId){
//		productService.delete(productId);
//	}
//	
//	public Mono<ServerResponse> getCount(ServerRequest request){
//		Mono<Long> count= productService.getCount();
//		
//		return ServerResponse.ok().body("Products Count is "+count.block());
//	}
//	
//	public Flux<ServerResponse> getProducts(){
//		Flux<Product> products=productService.getProducts();
//		return ServerResponse.ok().body(products).flux();
//	
//	}


}
