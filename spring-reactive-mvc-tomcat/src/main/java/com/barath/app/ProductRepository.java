package com.barath.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveCrudRepository<Product, Long>{
	
	
	@Override
	default Mono<Void> delete(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
