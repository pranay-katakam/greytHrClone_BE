package com.nineleaps.greytHRClone.controller;

import com.nineleaps.greytHRClone.model.EmployeeDepartment;
import com.nineleaps.greytHRClone.model.EmployeeDesignation;
import com.nineleaps.greytHRClone.service.AuthenticationService;
import com.nineleaps.greytHRClone.service.EmployeeDetailsService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RestController
public class EmployeeDetails {

    private AuthenticationService authenticationService;
    private EmployeeDetailsService employeeDetailsService;

    @Autowired
    public EmployeeDetails(AuthenticationService authenticationService, EmployeeDetailsService employeeDetailsService) {
        this.authenticationService = authenticationService;
        this.employeeDetailsService = employeeDetailsService;
    }


    @GetMapping(path = "/profile")
    public ResponseEntity<JSONObject> profile(@RequestParam(value = "id") int id) {
        return authenticationService.profile(id);
    }


    @GetMapping(path = "/events")
    public ResponseEntity<List<JSONObject>> events() {
        return employeeDetailsService.BirthdayList();
    }


    @PostMapping(path="/departments")
    public ResponseEntity<String> addDepartments(@RequestBody EmployeeDepartment employeeDepartment) {
        return employeeDetailsService.addDepartments(employeeDepartment);

    }
    @PostMapping(path="/designation")
    public ResponseEntity<String> addDesignation(@RequestBody EmployeeDesignation employeeDesignation) {
        System.out.println(employeeDesignation+"in controller");
        return employeeDetailsService.addDesignations(employeeDesignation);

    }
}

