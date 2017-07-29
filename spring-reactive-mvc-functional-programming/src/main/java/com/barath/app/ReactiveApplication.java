package com.barath.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ServletHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import com.barath.app.configuration.RouterConfiguration;
import com.barath.app.handler.ProductHandler;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class ReactiveApplication {

	public static void main(String[] args) {

		SpringApplication.run(ReactiveApplication.class, args);

	}

}
