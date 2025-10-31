package com.seanjefferies.addressable.util;

import com.seanjefferies.addressable.model.Address;

public class ParseAddressResult {
    private Address address;
    private String delimiter;

    public Address getAddress() {
        return address;
    }

    public ParseAddressResult setAddress(Address address) {
        this.address = address;
        return this;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public ParseAddressResult setDelimiter(String delimiter) {
        this.delimiter = delimiter;
        return this;
    }

    @Override
    public String toString() {
        return "ParseAddressResult{" +
                "address=" + address +
                ", delimiter='" + delimiter + '\'' +
                '}';
    }
}
