package com.packt.webstore.exception;

public class InvalidCustomerException extends RuntimeException {

	private static final long serialVersionUID = -5192041563033358491L;
	private String customerId;

	public InvalidCustomerException(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerId() {
		return customerId;
	}
}