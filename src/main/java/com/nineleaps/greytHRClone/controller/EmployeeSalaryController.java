package com.nineleaps.greytHRClone.controller;

import com.nineleaps.greytHRClone.dto.SalaryDTO;
import com.nineleaps.greytHRClone.model.EmployeeSalary;
import com.nineleaps.greytHRClone.service.EmployeeSalaryService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@Tag(name = "employee salary controller", description = "Controller class deals employee salary")
@RestController
public class EmployeeSalaryController {
    @Autowired
    EmployeeSalaryService employeeSalaryService;

    @PostMapping(path = "/addSalary")
    public ResponseEntity<String> addSalary( @RequestBody EmployeeSalary employeeSalary) {
        return employeeSalaryService.addSalary(employeeSalary);
    }

    @GetMapping(path="/getSalaryDetails")
    public ResponseEntity<List<SalaryDTO>> getSalaryDetails(@RequestAttribute("id") int eid){
        return employeeSalaryService.getSalaryDetails(eid);
    }

}
