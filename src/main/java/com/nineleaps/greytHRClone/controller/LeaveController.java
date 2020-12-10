package com.nineleaps.greytHRClone.controller;

import com.nineleaps.greytHRClone.dto.EmployeeLeaveDTO;

import com.nineleaps.greytHRClone.dto.EmployeeLeaveRequestDTO;
import com.nineleaps.greytHRClone.dto.HolidayDTO;
import com.nineleaps.greytHRClone.model.Holidays;
import com.nineleaps.greytHRClone.service.LeaveServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;


@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@Tag(name = "employee leaves controller", description = "Controller class deals with leave data to manage attendances")
@RestController
public class LeaveController {

    @Autowired
    private LeaveServices leaveServices;

    @Operation(summary = "Add new public holidays", description = "To add new public holidays based on date", tags = { "addHolidays" })

    @PostMapping(path = "/holidays")
    public ResponseEntity<String> addHolidays(@RequestBody List<HolidayDTO> holidays) {

        return leaveServices.addHolidays(holidays);
    }

    @Operation(summary = "View all the available holidays", description = "To get all available holidays", tags = { "getHolidays" })
    @GetMapping(path = "/holidays")
    public ResponseEntity<List<Holidays>> getHolidays() {
        return leaveServices.getHolidays();
    }

    @Operation(summary = "View upcoming holidays", description = "To get upcoming holidays", tags = { "getUpcomingHolidays" })
    @GetMapping(path = "/upcoming-holidays")
    public ResponseEntity<List<Holidays>> getUpcomingHolidays() {
        return leaveServices.getUpcomingHolidays();
    }


    @Operation(summary = "apply for a leave", description = "apply leave for a required date", tags = { "applyLeave" })
    @PostMapping("/leave")
    public ResponseEntity<String> applyLeave(@RequestBody EmployeeLeaveRequestDTO employeeLeaveRequestDTO){
        return  leaveServices.applyLeave(employeeLeaveRequestDTO);
    }

    @Operation(summary = "View all leaves taken", description = "To get list of all leaves taken", tags = { "getLeaves" })
    @GetMapping("/leaves")
    public ResponseEntity<List<EmployeeLeaveDTO>> getLeaves(@RequestAttribute int id, @RequestParam(value = "year", required = false )Year year){
        return leaveServices.getLeaves( id,year);
    }

    //TODO manager to approve applied leaves
    @GetMapping("/approveLeaves")
    public ResponseEntity<String> approveLeaves(@RequestAttribute int id ){
        return leaveServices.approveLeaves( id);
    }

}
