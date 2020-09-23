package com.nineleaps.greytHRClone.service;

import com.nineleaps.greytHRClone.controller.EmployeeSalaryController;
import com.nineleaps.greytHRClone.model.EmployeeSalary;
import com.nineleaps.greytHRClone.repository.EmployeeSalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EmployeeSalaryService {
    @Autowired
    private EmployeeSalaryRepository employeeSalaryRepository;

    public ResponseEntity<String> assignSalary(EmployeeSalary employeeSalary) {
        employeeSalaryRepository.save(employeeSalary);
        return ResponseEntity.status(HttpStatus.CREATED).body("salary added successfully");
    }

//    public ResponseEntity<SalaryDTO> getSalary(int id) {
//
////            return ResponseEntity.status(HttpStatus.OK).body(salaryDTO);
//        return null;
//        }
}
