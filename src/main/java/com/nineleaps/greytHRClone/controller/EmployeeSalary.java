package com.nineleaps.greytHRClone.controller;

import com.nineleaps.greytHRClone.service.EmployeeSalaryService;
import io.swagger.annotations.ApiOperation;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeSalary {

    @Autowired
    EmployeeSalaryService employeeSalaryService;

    @PostMapping(path="/salary")
    public ResponseEntity<String> assignSalary(@RequestBody EmployeeSalary employeeSalary){
        return employeeSalaryService.assignSalary(employeeSalary);
    }

    @ApiOperation(value = "To get salary of all the employees")
    @GetMapping(path = "/getSalary")
    public ResponseEntity<List<JSONObject>> getSalary(int id) {

        return employeeSalaryService.getSalary(id);
    }
}
