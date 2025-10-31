package com.seanjefferies.addressable.controller;

import com.seanjefferies.addressable.dto.AddressDto;
import com.seanjefferies.addressable.dto.AddressesByEntity;
import com.seanjefferies.addressable.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    private AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/list")
    public List<AddressDto> list() {
        return addressService.findAll();
    }

    @GetMapping("/count/street")
    public List<AddressesByEntity> countByStreet() {
        return addressService.countByStreet();
    }

    @GetMapping("/count/city")
    public List<AddressesByEntity> countByCity() {
        return addressService.countByCity();
    }

    @GetMapping("/count/state")
    public List<AddressesByEntity> countByState() {
        return addressService.countByState();
    }

    @GetMapping("/count/zip2")
    public List<AddressesByEntity> countByZip2() {
        return addressService.countByZip2();
    }
}
