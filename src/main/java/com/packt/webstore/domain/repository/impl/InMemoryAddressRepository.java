package com.packt.webstore.domain.repository.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.packt.webstore.domain.Address;
import com.packt.webstore.domain.repository.AddressRepository;
import com.packt.webstore.exception.AddressNotFoundException;

public class InMemoryAddressRepository implements AddressRepository {
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public Address getAddressById(Long addressId) {
		String SQL = "SELECT * FROM ADDRESS WHERE ID = :addressId";
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("addressId", String.valueOf(addressId));

		try {
			return jdbcTemplate.queryForObject(SQL, params, new AddressMapper());
		} catch (DataAccessException e) {
			throw new AddressNotFoundException(addressId);
		}
	}
}
