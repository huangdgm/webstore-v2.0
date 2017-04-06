package com.packt.webstore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packt.webstore.domain.Customer;
import com.packt.webstore.domain.repository.CustomerRepository;
import com.packt.webstore.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public List<Customer> getAllCustomers() {
		return customerRepository.getAllCustomers();
	}

	@Override
	public long saveCustomer(Customer customer) {
		return customerRepository.saveCustomer(customer);
	}

	@Override
	public Customer getCustomerById(Long customerId) {
		return customerRepository.getCustomerById(customerId);
	}

	@Override
	public Boolean isCustomerExist(String customerId) {
		return customerRepository.isCustomerExist(customerId);
	}
}