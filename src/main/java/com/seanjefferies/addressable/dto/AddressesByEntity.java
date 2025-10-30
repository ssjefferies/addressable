package com.seanjefferies.addressable.dto;

/**
 * The DTO for reports that count the number of times a give city, state,
 * zip2, etc... occurs in the data.
 *
 * IN this case, 'Entity' refers to the part of an address that the count
 * is applied to (city, state, zip2).
 */
public class AddressesByEntity {

    // Maybe overkill for now, but this gives us the ability
    // to enforce a strict set of entity types that we will query on.
    // Will help to guide development of the reports
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
