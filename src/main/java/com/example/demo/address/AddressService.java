package com.example.demo.address;

import org.springframework.stereotype.Service;


@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public void saveAddress(Address address) {
        addressRepository.save(address);
    }
}
