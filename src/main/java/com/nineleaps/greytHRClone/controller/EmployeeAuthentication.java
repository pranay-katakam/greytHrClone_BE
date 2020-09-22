package com.nineleaps.greytHRClone.controller;


import com.nineleaps.greytHRClone.model.EmployeeData;
import com.nineleaps.greytHRClone.service.AuthenticationService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public ResponseEntity<JSONObject> Login(@RequestBody EmployeeData userCredentials, HttpServletResponse response) {
        return authenticationService.Login(userCredentials,response);
    }

    @GetMapping(path="/logout")
    public ResponseEntity<String> Logout(HttpServletRequest request, HttpServletResponse response){
        return authenticationService.Logout(request,response);
    }

}
