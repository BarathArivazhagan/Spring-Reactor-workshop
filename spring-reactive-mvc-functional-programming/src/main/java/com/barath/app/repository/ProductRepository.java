package com.barath.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.barath.app.entity.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.lang.String;

public interface ProductRepository extends CrudRepository<Product, Long>{
	
	 Product findByProductName(String productname);
}
