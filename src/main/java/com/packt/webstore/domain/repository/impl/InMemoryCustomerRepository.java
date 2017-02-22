package com.packt.webstore.domain.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.packt.webstore.domain.Customer;
import com.packt.webstore.domain.repository.CustomerRepository;

@Repository
public class InMemoryCustomerRepository implements CustomerRepository {
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<Customer> getAllCustomers() {
		Map<String, Object> params = new HashMap<String, Object>();

		List<Customer> result = jdbcTemplate.query("SELECT * FROM customer", params, new CustomerMapper());

		return result;
	}
}

final class CustomerMapper implements RowMapper<Customer> {
	public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Customer customer = new Customer();

		customer.setCustomerId(rs.getLong("ID"));
		customer.setName(rs.getString("NAME"));
//		customer.setBillingAddress(rs.getInt("BILLING_ADDRESS_ID"));
		customer.setPhoneNumber(rs.getString("PHONE_NUMBER"));

		return customer;
	}
}
