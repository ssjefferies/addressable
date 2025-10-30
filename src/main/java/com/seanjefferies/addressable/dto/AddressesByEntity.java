package com.seanjefferies.addressable.dto;

public class AddressesByEntity {

    public static enum AddressEntityTypes {
        CITY,
        STATE,
        ZIPCODE
    }

    private AddressEntityTypes entity;
    private final String entityName;
    private final long count;

    public AddressesByEntity(String entityType, String entityName, long count) {
        switch(entityType) {
            case "CITY":
                this.entity = AddressEntityTypes.CITY;
                break;
            case "STATE":
                this.entity = AddressEntityTypes.STATE;
                break;
            case "ZIPCODE":
                this.entity = AddressEntityTypes.ZIPCODE;
                break;
        }
        this.entityName = entityName;
        this.count = count;
    }

    public AddressEntityTypes getEntity() {
        return entity;
    }

    public String getEntityName() {
        return entityName;
    }

    public long getCount() {
        return count;
    }
}
