package com.nineleaps.greytHRClone.controller;

import com.nineleaps.greytHRClone.model.DoorAddress;
import com.nineleaps.greytHRClone.model.EmployeeDesignation;
import com.nineleaps.greytHRClone.model.Swipes;
import com.nineleaps.greytHRClone.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@Tag(name = "employee swipe controller", description = "Controller class deals with swipes made by employees")
@RestController
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    @PostMapping(path = "/door-address")
    public ResponseEntity<String> addDoorAddress(@RequestBody Iterable<DoorAddress> doorAddresses) {
        return attendanceService.addDoorAddress(doorAddresses);
    }


    @GetMapping(path = "/door-address")
    public ResponseEntity<Iterable<DoorAddress>> getDoorAddress() {
        return attendanceService.getDoorAddress();
    }

}
