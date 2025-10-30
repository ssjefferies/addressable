package com.seanjefferies.addressable.service;

import com.seanjefferies.addressable.dto.AddressDto;
import com.seanjefferies.addressable.dto.AddressesByEntity;
import com.seanjefferies.addressable.model.Address;
import com.seanjefferies.addressable.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {

    private AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<AddressDto> findAll() {
        return modelsToDtos(addressRepository.findAll());
    }

    public List<AddressesByEntity> countByCity() {
        return addressRepository.getCountsByCity();
    }

    public List<AddressesByEntity> countByState() {
        return addressRepository.getCountsByState();
    }

    public static List<AddressDto> modelsToDtos(List<Address> addresses) {
        return addresses.stream()
                .map(AddressService::modelToDto)
                .collect(Collectors.toList());
    }

    public static AddressDto modelToDto(Address address) {
        return new AddressDto()
                .setStreet(address.getStreet())
                .setCity(address.getCity())
                .setState(address.getState())
                .setZipcode(address.getZip());
    }
}
