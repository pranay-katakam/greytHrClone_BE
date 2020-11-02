package com.nineleaps.greytHRClone.controller;

import com.nineleaps.greytHRClone.dto.EmployeeLeaveDTO;
import com.nineleaps.greytHRClone.model.EmployeeLeave;
import com.nineleaps.greytHRClone.model.Holidays;
import com.nineleaps.greytHRClone.service.LeaveServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@Tag(name = "employee leaves controller", description = "Controller class deals with leave data to manage attendances")
@RestController
public class LeaveController {

    @Autowired
    private LeaveServices leaveServices;


    @PostMapping(path = "/holidays")
    public ResponseEntity<String> addHolidays(@RequestBody Iterable<Holidays> holidays) {
        return leaveServices.addHolidays(holidays);
    }

    @GetMapping(path = "/holidays")
    public ResponseEntity<Iterable<Holidays>> getHolidays() {
        return leaveServices.getHolidays();
    }

    @PostMapping("/leave")
    public ResponseEntity<String> applyLeave(@RequestBody EmployeeLeave employeeleave){
        return  leaveServices.applyLeave(employeeleave);
    }

    @GetMapping("/leaves")
    public ResponseEntity<List<EmployeeLeaveDTO>> getLeaves(@RequestAttribute int id ){
        return leaveServices.getLeaves( id);
    }
}
