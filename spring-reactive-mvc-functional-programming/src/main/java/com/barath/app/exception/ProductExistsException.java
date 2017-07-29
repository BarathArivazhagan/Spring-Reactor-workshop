package com.barath.app.exception;

public class ProductExistsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1521769416397255193L;
	
	public ProductExistsException() {
		
	}
	
	public ProductExistsException(String errorMessage){
		super(errorMessage);
	}
	
	public ProductExistsException(Throwable throwable){
		super(throwable);
	}
	
	

}
