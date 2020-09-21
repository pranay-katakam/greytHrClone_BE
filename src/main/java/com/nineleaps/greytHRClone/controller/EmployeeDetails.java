package com.nineleaps.greytHRClone.controller;

import com.nineleaps.greytHRClone.dto.EventDTO;
import com.nineleaps.greytHRClone.dto.ProfileDTO;
import com.nineleaps.greytHRClone.model.EmployeeDepartment;
import com.nineleaps.greytHRClone.model.EmployeeDesignation;
import com.nineleaps.greytHRClone.service.AuthenticationService;
import com.nineleaps.greytHRClone.service.EmployeeDetailsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@Api(value = "Controller class deals employee details")
@RestController
public class EmployeeDetails {

    @Autowired
    EmployeeDetailsService employeeDetailsService;

    @ApiOperation(value = "To get the profile details of employee")
    @GetMapping(path = "/profile")
    public ResponseEntity<ProfileDTO> profile(@RequestAttribute("id") int id) {
        System.out.println("idvalue"+id);
        return employeeDetailsService.profile(id);
    }

    @GetMapping(path = "/events")
    public ResponseEntity<List<EventDTO>> events(){
        return employeeDetailsService.events();
    }

    @PostMapping(path = "/department")
    public ResponseEntity<String> addDepartment(@RequestBody EmployeeDepartment employeeDepartment){
       return employeeDetailsService.addDepartment(employeeDepartment);
    }

    @PostMapping(path = "/designation")
    public ResponseEntity<String> addDesignation(@Valid @RequestBody EmployeeDesignation employeeDesignation){
        return employeeDetailsService.addDesignation(employeeDesignation);
    }

    @GetMapping(path="/departments")
    public ResponseEntity<Iterable<EmployeeDepartment>> getDepartments(){
        return employeeDetailsService.getDepartments();
    }

    @GetMapping(path="/designations")
    public ResponseEntity<Iterable<EmployeeDesignation>> getDesignations(){
        return employeeDetailsService.getDesignations();
    }





}
