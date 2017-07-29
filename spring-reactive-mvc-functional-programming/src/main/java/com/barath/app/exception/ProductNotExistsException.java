package com.barath.app.exception;

public class ProductNotExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1521769416397255193L;
	
	public ProductNotExistsException() {
		
	}
	
	public ProductNotExistsException(String errorMessage){
		super(errorMessage);
	}
	
	public ProductNotExistsException(Throwable throwable){
		super(throwable);
	}
}
