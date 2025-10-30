package com.seanjefferies.addressable.repository;

import static com.seanjefferies.addressable.dto.AddressesByEntity.AddressEntityTypes;

import com.seanjefferies.addressable.dto.AddressesByEntity;
import com.seanjefferies.addressable.model.Address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("select new com.seanjefferies.addressable.dto.AddressesByEntity('CITY', a.city, count(a)) from Address a group by a.city order by a.city asc")
    List<AddressesByEntity> getCountsByCity();

    @Query("select new com.seanjefferies.addressable.dto.AddressesByEntity('STATE', a.state, count(a)) from Address a group by a.state order by a.state asc")
    List<AddressesByEntity> getCountsByState();

    @Query("select new com.seanjefferies.addressable.dto.AddressesByEntity('ZIP2', substring(a.zip, 1,2), count(a)) from Address a group by substring(a.zip, 1,2) order by substring(a.zip, 1,2) asc")
    List<AddressesByEntity> getCountsByZip2();

}
