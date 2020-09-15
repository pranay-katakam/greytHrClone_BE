package com.nineleaps.greytHRClone.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.nineleaps.greytHRClone.model.EmployeeData;
import com.nineleaps.greytHRClone.service.AuthenticationService;

//import org.json.JSONObject;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RestController
public class EmployeeAuthentication {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping(path = "/employee")
    public ResponseEntity<String> Signup(@RequestBody EmployeeData employeeData) {
        return authenticationService.Signup(employeeData);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> Login(@RequestBody EmployeeData userCredentials) {
        return authenticationService.Login(userCredentials);
    }
}
