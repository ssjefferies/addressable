package com.seanjefferies.addressable.repository;

import com.seanjefferies.addressable.dto.AddressesByEntity;
import com.seanjefferies.addressable.model.Address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Provides basic CRUD operations for the Address entity.
 * Also includes custom queries for reports that need a count
 * for each city, state, zip2, etc..
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("select new com.seanjefferies.addressable.dto.AddressesByEntity('CITY', a.city, count(a)) from Address a group by a.city order by a.city asc")
    List<AddressesByEntity> getCountsByCity();

    @Query("select new com.seanjefferies.addressable.dto.AddressesByEntity('STATE', a.state, count(a)) from Address a group by a.state order by a.state asc")
    List<AddressesByEntity> getCountsByState();

    /**
     * select count(*), a.street_name
     * from addresses a
     * group by a.street_name
     * order by count(*) desc
     * @return
     */
    @Query("select new com.seanjefferies.addressable.dto.AddressesByEntity('STREET', a.streetName, count(a)) from Address a group by a.streetName order by count(a) desc, a.streetName asc")
    List<AddressesByEntity> getCountsByStreet();

    @Query("select new com.seanjefferies.addressable.dto.AddressesByEntity('ZIP2', substring(a.zip, 1,2), count(a)) from Address a group by substring(a.zip, 1,2) order by substring(a.zip, 1,2) asc")
    List<AddressesByEntity> getCountsByZip2();

}
