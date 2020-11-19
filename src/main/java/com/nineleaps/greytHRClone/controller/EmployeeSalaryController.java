package com.nineleaps.greytHRClone.controller;

import com.nineleaps.greytHRClone.dto.AnnualEarningsDTO;
import com.nineleaps.greytHRClone.dto.EmployeeSalaryRequestDTO;

import com.nineleaps.greytHRClone.service.EmployeeSalaryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@Tag(name = "employee salary controller", description = "Controller class deals employee salary")
@RestController
public class EmployeeSalaryController {
    @Autowired
    EmployeeSalaryService employeeSalaryService;

    @Operation(summary = "to add total salary for a employee ", description = "To add total salary for each employee", tags = {"addSalary"})
    @PostMapping(path = "/addSalary")
    public ResponseEntity<String> addSalary(@RequestBody EmployeeSalaryRequestDTO employeeSalaryRequestDTO) {
        return employeeSalaryService.addSalary(employeeSalaryRequestDTO);
    }

    @Operation(summary = "To get employee salary details", description = "To get employee salary details based on requested id", tags = {"getSalaryDetails"})
    @GetMapping(path = "/getSalaryDetails")
    public ResponseEntity<AnnualEarningsDTO> getSalaryDetails(@RequestAttribute("id") int eid) {
        return employeeSalaryService.getSalaryDetails(eid);
    }

}
