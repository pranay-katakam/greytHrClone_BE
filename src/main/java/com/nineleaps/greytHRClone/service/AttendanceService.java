package com.nineleaps.greytHRClone.service;

import com.nineleaps.greytHRClone.model.DoorAddress;
import com.nineleaps.greytHRClone.model.Swipe;
import com.nineleaps.greytHRClone.repository.DoorAddressRepository;
import com.nineleaps.greytHRClone.repository.SwipesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {


    private DoorAddressRepository doorAddressRepository;
    private SwipesRepository swipesRepository;

    public AttendanceService(DoorAddressRepository doorAddressRepository, SwipesRepository swipesRepository) {
        this.doorAddressRepository = doorAddressRepository;
        this.swipesRepository = swipesRepository;
    }

    public ResponseEntity<String> addDoorAddress(Iterable<DoorAddress> doorAddresses) {
        doorAddressRepository.saveAll(doorAddresses);
        return ResponseEntity.status(HttpStatus.CREATED).body("added door address successfully!");
    }

    public ResponseEntity<Iterable<DoorAddress>> getDoorAddress() {
        return ResponseEntity.status(HttpStatus.OK).body(doorAddressRepository.findAll());
    }

    public ResponseEntity<String> addSwipe(Swipe swipe) {
        swipesRepository.save(swipe);
        return ResponseEntity.status(HttpStatus.CREATED).body("recent swipe recorded successfully");

    }

//    public ResponseEntity<Iterable<Swipe>> getSwipes() {
//        return ResponseEntity.status(HttpStatus.OK).body(swipesRepository.findAll());
//
//    }
}
