package com.nineleaps.greytHRClone.controller;

import com.nineleaps.greytHRClone.dto.SalaryDTO;
import com.nineleaps.greytHRClone.model.EmployeeSalary;
import com.nineleaps.greytHRClone.service.EmployeeSalaryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@Api(value = "Controller class deals employee salary")
@RestController
public class EmployeeSalaryController {
    @Autowired
    EmployeeSalaryService employeeSalaryService;

    @PostMapping(path = "/addSalary")
    public ResponseEntity<String> addSalary( @RequestBody EmployeeSalary employeeSalary) {
        return employeeSalaryService.addSalary(employeeSalary);
    }


}
