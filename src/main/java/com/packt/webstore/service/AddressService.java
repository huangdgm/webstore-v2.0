package com.packt.webstore.service;

import com.packt.webstore.domain.Address;

public interface AddressService {
	Address getAddressById(Long addressId);
}