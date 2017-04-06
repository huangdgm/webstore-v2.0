package com.packt.webstore.domain.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.packt.webstore.domain.Address;

public class AddressMapper implements RowMapper<Address> {
	public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
		Address address = new Address();

		address.setId(rs.getLong("ID"));
		address.setDoorNo(rs.getString("DOOR_NO"));
		address.setStreetName(rs.getString("STREET_NAME"));
		address.setAreaName(rs.getString("AREA_NAME"));
		address.setState(rs.getString("STATE"));
		address.setCountry(rs.getString("COUNTRY"));
		address.setZipCode(rs.getString("ZIP"));

		return address;
	}
}