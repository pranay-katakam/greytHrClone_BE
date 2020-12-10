package com.nineleaps.greytHRClone.controller;

import com.nineleaps.greytHRClone.dto.*;
import com.nineleaps.greytHRClone.model.DoorAddress;
import com.nineleaps.greytHRClone.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


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

    //TODO make dto to handle start date and end date
    @Operation(summary = "View the attendance summary based on id ", description = "To get attendance summary of employee based  on record calculated from month begin to current date", tags = { "getMonthSummary" })
    @GetMapping(path = "/attendance-summary")
    public ResponseEntity<AttendanceSummaryDTO> getAttendanceSummary(@RequestAttribute("id") int id , @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return attendanceService.getAttendanceSummary(id,startDate,endDate);
    }




}
