package com.packt.webstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.packt.webstore.domain.Address;
import com.packt.webstore.domain.repository.AddressRepository;
import com.packt.webstore.service.AddressService;

public class AddressServiceImpl implements AddressService {
	@Autowired
	private AddressRepository addressRepository;

	@Override
	public Address getAddressById(Long addressId) {
		return addressRepository.getAddressById(addressId);
	}
}