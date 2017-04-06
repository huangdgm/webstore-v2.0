package com.packt.webstore.domain.repository;

import com.packt.webstore.domain.Address;

public interface AddressRepository {
	Address getAddressById(Long addressId);
}
