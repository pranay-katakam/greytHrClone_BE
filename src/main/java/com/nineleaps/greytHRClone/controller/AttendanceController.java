package com.nineleaps.greytHRClone.controller;

import com.nineleaps.greytHRClone.dto.ApiResponseDTO;
import com.nineleaps.greytHRClone.dto.DoorAddressDTO;
import com.nineleaps.greytHRClone.dto.SwipeDTO;
import com.nineleaps.greytHRClone.dto.SwipesDTO;
import com.nineleaps.greytHRClone.model.DoorAddress;
import com.nineleaps.greytHRClone.model.Swipe;
import com.nineleaps.greytHRClone.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@Tag(name = "employee swipe controller", description = "Controller class deals with swipes made by employees")
@RestController
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    @Operation(summary = "Add new door-address ", description = "To add all the avaliable door-address in organization", tags = { "addDoorAddress" })
    @PostMapping(path = "/door-address")
    public ResponseEntity<ApiResponseDTO> addDoorAddress(@RequestBody List<DoorAddressDTO> doorAddresses) {
        return attendanceService.addDoorAddress(doorAddresses);
    }

    @Operation(summary = "View all the door-address ", description = "To get all available door-address added in organization", tags = { "getDoorAddress" })
    @GetMapping(path = "/door-address")
    public ResponseEntity<List<DoorAddress>> getDoorAddress() {
        return attendanceService.getDoorAddress();
    }

    @Operation(summary = "record recent swipes", description = "To record the recent swipes done by employee", tags = { "addSwipe" })
    @PostMapping(path = "/swipe")
    public ResponseEntity<ApiResponseDTO> addSwipe(@RequestBody SwipeDTO swipeDTO) {
        return attendanceService.addSwipe(swipeDTO);
    }


    @Operation(summary = "View all the swipes record of a employee ", description = "To get swipes record of employee based on date", tags = { "getSwipes" })
    @GetMapping(path = "/swipes")
    public ResponseEntity<List<SwipesDTO>> getSwipes(@RequestAttribute("id") int id) {
        return attendanceService.getSwipes(id);
    }

    @Operation(summary = "View recent 4 swipes record of a employee ", description = "To get recent swipes record of employee based on date", tags = { "getRecentSwipes" })
    @GetMapping(path = "/recentSwipes")
    public ResponseEntity<List<SwipesDTO>> getRecentSwipes(@RequestAttribute("id") int id) {
        return attendanceService.getRecentSwipes(id);
    }


}
