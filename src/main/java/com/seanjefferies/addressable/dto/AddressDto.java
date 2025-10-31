package com.seanjefferies.addressable.dto;

/**
 * This is a DTO that represents the address information
 * included in reports that require a full address in the report.

 * For instance, this is used when a client requests
 * a full list of the addresses in the system.
 */
public class AddressDto {
    private Long id;

    private String fullStreet;

    // parts of a full street address
    // to enable better reporting
    private String streetNumber;
    private String streetName;
    private String unit;

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

    public String getFullStreet() {
        return fullStreet;
    }

    public AddressDto setFullStreet(String fullStreet) {
        this.fullStreet = fullStreet;
        return this;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public AddressDto setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
        return this;
    }

    public String getStreetName() {
        return streetName;
    }

    public AddressDto setStreetName(String streetName) {
        this.streetName = streetName;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public AddressDto setUnit(String unit) {
        this.unit = unit;
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
