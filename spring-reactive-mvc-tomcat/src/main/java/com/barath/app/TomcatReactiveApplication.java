package com.barath.app;

import org.apache.catalina.startup.Tomcat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ServletHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import javax.annotation.PostConstruct;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;

@SpringBootApplication
public class TomcatReactiveApplication {
	
	public static final String HOST = "localhost";

	public static final int PORT = 9002;
	
	@Autowired
	private RouterConfiguration routerConf;

	public static void main(String[] args) {
		SpringApplication.run(TomcatReactiveApplication.class, args);
		
	}
	
	@PostConstruct
	public void startTomcat() throws LifecycleException{
		RouterFunction<?> route = routerConf.getRoutingFunctions();
		HttpHandler httpHandler = RouterFunctions.toHttpHandler(route);

		Tomcat tomcatServer = new Tomcat();
		tomcatServer.setHostname(HOST);
		tomcatServer.setPort(PORT);
		Context rootContext = tomcatServer.addContext("", System.getProperty("java.io.tmpdir"));
		ServletHttpHandlerAdapter servlet = new ServletHttpHandlerAdapter(httpHandler);
		Tomcat.addServlet(rootContext, "httpHandlerServlet", servlet);
		rootContext.addServletMapping("/", "httpHandlerServlet");
		tomcatServer.start();
	}
}
