package com.packt.webstore.domain.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.packt.webstore.domain.Address;
import com.packt.webstore.domain.Customer;
import com.packt.webstore.domain.repository.CustomerRepository;
import com.packt.webstore.exception.CustomerNotFoundException;
import com.packt.webstore.service.AddressService;

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

	@Override
	public long saveCustomer(Customer customer) {
		long addressId = saveAddress(customer.getBillingAddress());

		String SQL = "INSERT INTO CUSTOMER(NAME,PHONE_NUMBER,BILLING_ADDRESS_ID) " + "VALUES (:name, :phoneNumber, :addressId)";

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("name", customer.getName());
		params.put("phoneNumber", customer.getPhoneNumber());
		params.put("addressId", addressId);

		SqlParameterSource paramSource = new MapSqlParameterSource(params);

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(SQL, paramSource, keyHolder, new String[] { "ID" });

		return keyHolder.getKey().longValue();
	}

	@Override
	public Customer getCustomerById(long customerId) {
		String SQL = "SELECT * FROM CUSTOMER WHERE ID = :customerId";
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("customerId", String.valueOf(customerId));

		try {
			return jdbcTemplate.queryForObject(SQL, params, new CustomerMapper());
		} catch (DataAccessException e) {
			throw new CustomerNotFoundException(customerId);
		}
	}

	@Override
	public Boolean isCustomerExist(String customerId) {
		List<Customer> customers = this.getAllCustomers();

		for (Customer customer : customers) {
			String id = String.valueOf(customer.getCustomerId());

			if (id.equals(customerId)) {
				return true;
			}
		}

		return false;
	}

	private long saveAddress(Address address) {
		String SQL = "INSERT INTO ADDRESS(DOOR_NO,STREET_NAME,AREA_NAME,STATE,COUNTRY,ZIP) "
				+ "VALUES (:doorNo, :streetName, :areaName, :state, :country, :zip)";

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("doorNo", address.getDoorNo());
		params.put("streetName", address.getStreetName());
		params.put("areaName", address.getAreaName());
		params.put("state", address.getState());
		params.put("country", address.getCountry());
		params.put("zip", address.getZipCode());

		SqlParameterSource paramSource = new MapSqlParameterSource(params);

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(SQL, paramSource, keyHolder, new String[] { "ID" });

		return keyHolder.getKey().longValue();
	}
}

final class CustomerMapper implements RowMapper<Customer> {
	@Autowired
	private AddressService addressService;

	public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Customer customer = new Customer();
		long addressId = rs.getLong("BILLING_ADDRESS_ID");
		Address address = addressService.getAddressById(addressId);

		customer.setCustomerId(rs.getLong("ID"));
		customer.setName(rs.getString("NAME"));
		customer.setBillingAddress(address);
		customer.setPhoneNumber(rs.getString("PHONE_NUMBER"));

		return customer;
	}
}