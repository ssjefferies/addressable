package com.seanjefferies.addressable.dto;

/**
 * This is a DTO that represents the address information
 * included in reports that require a full address in the report.

 * For instance, this is used when a client requests
 * a full list of the addresses in the system.
 */
public class AddressDto {
    private Long id;
    private String street;
    private String city;
    private String state;
    private String zipcode;

    public Long getId() {
        return id;
    }

    public AddressDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public AddressDto setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getCity() {
        return city;
    }

    public AddressDto setCity(String city) {
        this.city = city;
        return this;
    }

    public String getState() {
        return state;
    }

    public AddressDto setState(String state) {
        this.state = state;
        return this;
    }

    public String getZipcode() {
        return zipcode;
    }

    public AddressDto setZipcode(String zipcode) {
        this.zipcode = zipcode;
        return this;
    }
}
