package com.nineleaps.greytHRClone.service;

import com.nineleaps.greytHRClone.model.DoorAddress;
import com.nineleaps.greytHRClone.repository.DoorAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {

    @Autowired
    private DoorAddressRepository doorAddressRepository;

    public ResponseEntity<String> addDoorAddress(Iterable<DoorAddress> doorAddresses) {
        doorAddressRepository.saveAll(doorAddresses);
        return ResponseEntity.status(HttpStatus.CREATED).body("added door address successfully!");
    }

    public ResponseEntity<Iterable<DoorAddress>> getDoorAddress() {
        return ResponseEntity.status(HttpStatus.OK).body(doorAddressRepository.findAll());
    }
}
