package com.nineleaps.greytHRClone.service;

import com.nineleaps.greytHRClone.dto.SalaryDTO;
import com.nineleaps.greytHRClone.model.EmployeeSalary;
import com.nineleaps.greytHRClone.repository.EmployeeSalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmployeeSalaryService {

    @Autowired
    EmployeeSalaryRepository employeeSalaryRepository;

    public ResponseEntity<String> addSalary(EmployeeSalary employeeSalary) {
        System.out.println(employeeSalary);
        employeeSalaryRepository.save(employeeSalary);
        return ResponseEntity.status(HttpStatus.CREATED).body("Salary added successfully");

    }


}
