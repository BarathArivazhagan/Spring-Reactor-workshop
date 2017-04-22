package com.barath.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterConfiguration {
	
	private ProductHandler productHandler;
	
	@Autowired
	public RouterConfiguration(ProductHandler productHandler){
		this.productHandler=productHandler;
	}
	
	
	public RouterFunction<ServerResponse> getRoutingFunctions(){
		
		return RouterFunctions.route(POST("/product/save").and(RequestPredicates.accept(org.springframework.http.MediaType.APPLICATION_JSON_UTF8)),productHandler::saveProduct)
				
				.andRoute(GET("/product/get/{id}").and(RequestPredicates.accept(org.springframework.http.MediaType.APPLICATION_JSON_UTF8)),productHandler::getProduct);
		
	}

}
