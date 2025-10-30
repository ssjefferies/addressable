package com.seanjefferies.addressable.dto;

public class AddressesByEntity {

    public static enum AddressEntityTypes {
        CITY,
        STATE,
        ZIP2
    }

    private AddressEntityTypes type;
    private final String name;
    private final long count;

    public AddressesByEntity(String entityType, String entityName, long count) {
        switch(entityType) {
            case "CITY":
                this.type = AddressEntityTypes.CITY;
                break;
            case "STATE":
                this.type = AddressEntityTypes.STATE;
                break;
            case "ZIP2":
                this.type = AddressEntityTypes.ZIP2;
                break;
        }
        this.name = entityName;
        this.count = count;
    }

    public AddressEntityTypes getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public long getCount() {
        return count;
    }
}
