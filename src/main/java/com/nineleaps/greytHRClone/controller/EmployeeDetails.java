package com.nineleaps.greytHRClone.controller;


import com.nineleaps.greytHRClone.dto.EventDTO;
import com.nineleaps.greytHRClone.dto.ProfileDTO;
import com.nineleaps.greytHRClone.model.EmployeeData;
import com.nineleaps.greytHRClone.model.EmployeeDepartment;
import com.nineleaps.greytHRClone.model.EmployeeDesignation;
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
        return employeeDetailsService.profile(id);
    }



    @ApiOperation(value = "To add required departments")
    @PostMapping(path = "/department")
    public ResponseEntity<String> addDepartment(@RequestBody EmployeeDepartment employeeDepartment) {
        return employeeDetailsService.addDepartment(employeeDepartment);
    }

    @ApiOperation(value = "To add required designation")
    @PostMapping(path = "/designation")
    public ResponseEntity<String> addDesignation(@Valid @RequestBody EmployeeDesignation employeeDesignation){

        return employeeDetailsService.addDesignation(employeeDesignation);
    }

    @ApiOperation(value = "To get avaliable departments")
    @GetMapping(path = "/departments")
    public ResponseEntity<Iterable<EmployeeDepartment>> getDepartments() {
        return employeeDetailsService.getDepartments();
    }

    @ApiOperation(value = "To get avaliable designations")
    @GetMapping(path = "/designations")
    public ResponseEntity<Iterable<EmployeeDesignation>> getDesignations() {
        return employeeDetailsService.getDesignations();
    }

    @ApiOperation(value = "To get list of all employees can be assigned as manager")
    @GetMapping(path = "/managers")
    public ResponseEntity<List<JSONObject>> getManagers() {
        return employeeDetailsService.getManagers();
    }


    @ApiOperation(value = "assign managers to an employee")
    @PatchMapping(path="/assignManager")
    public ResponseEntity<String> assignManager(@RequestParam(value = "mid") int mid,@RequestParam(value = "eid") int eid){
        return employeeDetailsService.assignManagers(mid,eid);
    }


}
