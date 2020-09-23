package com.nineleaps.greytHRClone.service;

import com.nineleaps.greytHRClone.controller.EmployeeSalary;
import com.nineleaps.greytHRClone.repository.EmployeeSalaryRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeSalaryService {
    @Autowired
    EmployeeSalaryRepository employeeSalaryRepository;

    public ResponseEntity<String> assignSalary(EmployeeSalary employeeSalary) {
//        employeeSalaryRepository.save(employeeSalary);
        return ResponseEntity.status(HttpStatus.CREATED).body("salary added successfully");
    }

    public ResponseEntity<List<JSONObject>> getSalary(int id) {

            return ResponseEntity.status(HttpStatus.OK).body(employeeSalaryRepository.getAllSalary(id));
        }
}
