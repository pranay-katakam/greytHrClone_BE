package com.nineleaps.greytHRClone.controller;

import com.nineleaps.greytHRClone.model.Feed;
import com.nineleaps.greytHRClone.model.Holidays;
import com.nineleaps.greytHRClone.service.FeedService;
import com.nineleaps.greytHRClone.service.LeaveServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@Tag(name = "employee leaves controller", description = "Controller class deals with leave data to manage attendances")
@RestController
public class LeaveController {

    @Autowired
    private LeaveServices leaveServices;


    @PostMapping(path = "/holidays")
    public ResponseEntity<String> addHolidays( @RequestBody  Iterable<Holidays> holidays) {
        return leaveServices.addHolidays(holidays);
    }

    @GetMapping(path = "/holidays")
    public ResponseEntity<Iterable<Holidays>> getHolidays() {
        return leaveServices.getHolidays();
    }





}
