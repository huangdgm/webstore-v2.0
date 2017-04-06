package com.packt.webstore.exception;

public class CustomerNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private Long customerId; 
	 
    public CustomerNotFoundException(Long customerId) { 
       this.customerId = customerId; 
    } 
     
    public Long getCustomerId() { 
       return customerId; 
    } 
}