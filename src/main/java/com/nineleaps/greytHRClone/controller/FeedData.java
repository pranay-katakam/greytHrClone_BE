package com.nineleaps.greytHRClone.controller;

import com.nineleaps.greytHRClone.dto.EventDTO;
import com.nineleaps.greytHRClone.service.FeedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@Api(value = "Controller class deals with feed data to manage events and posts")
@RestController
public class FeedData {
    @Autowired
    private FeedService feedService;


    @ApiOperation(value = "To get the list of birthdays and anniversery of employees")
    @GetMapping(path = "/events")
    public ResponseEntity<List<EventDTO>> events() {
        return feedService.events();

    }
}
