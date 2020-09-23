package com.nineleaps.greytHRClone.controller;

import com.nineleaps.greytHRClone.model.EmployeeSalary;
import com.nineleaps.greytHRClone.service.EmployeeSalaryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeSalaryController {

    @Autowired
    EmployeeSalaryService employeeSalaryService;

    @PostMapping(path="/salary")
    public ResponseEntity<String> assignSalary(@RequestBody EmployeeSalary employeeSalary){
        return employeeSalaryService.assignSalary(employeeSalary);
    }

}
