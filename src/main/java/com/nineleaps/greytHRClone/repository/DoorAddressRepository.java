package com.nineleaps.greytHRClone.repository;


import com.nineleaps.greytHRClone.model.DoorAddress;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DoorAddressRepository extends CrudRepository<DoorAddress, Integer> {
    @Query("select u from DoorAddress u")
    List<DoorAddress> getDoorAddress();
}
