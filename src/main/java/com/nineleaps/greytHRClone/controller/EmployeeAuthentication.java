package com.nineleaps.greytHRClone.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.nineleaps.greytHRClone.model.EmployeeData;
import com.nineleaps.greytHRClone.service.AuthenticationService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class EmployeeAuthentication {

    @Autowired
    AuthenticationService authenticationService;


   @PostMapping(path="/employee")
    public String Signup(@RequestBody EmployeeData employeeData){
      return authenticationService.Signup(employeeData);
   }







   @GetMapping(path = "/profile")
    public EmployeeData profile(@RequestParam(value = "id") int id){
       return authenticationService.profile(id);
   }





}
