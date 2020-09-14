package com.nineleaps.greytHRClone.service;

import com.nineleaps.greytHRClone.model.EmployeeDepartment;
import com.nineleaps.greytHRClone.model.EmployeeDesignation;
import com.nineleaps.greytHRClone.repository.EmployeeDataRepository;
import com.nineleaps.greytHRClone.repository.EmployeeDepartmentRepository;
import com.nineleaps.greytHRClone.repository.EmployeeDesignationRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Service
public class EmployeeDetailsService {

    private EmployeeDataRepository employeeDataRepository;
    private EmployeeDesignationRepository employeeDesignationRepository;
    private EmployeeDepartmentRepository employeeDepartmentRepository;

    @Autowired
    public EmployeeDetailsService(EmployeeDataRepository employeeDataRepository, EmployeeDesignationRepository employeeDesignationRepository, EmployeeDepartmentRepository employeeDepartmentRepository) {
        this.employeeDataRepository = employeeDataRepository;
        this.employeeDesignationRepository = employeeDesignationRepository;
        this.employeeDepartmentRepository = employeeDepartmentRepository;
    }

    public ResponseEntity<List<JSONObject>> BirthdayList() {
        List<JSONObject> birthdayList = employeeDataRepository.BirthdayList();
        System.out.println("BIRTHDAY" + birthdayList);
        List<JSONObject> anniversaryList = employeeDataRepository.AnniversaryList();
        System.out.println("ANIVERSERY" + anniversaryList);
        return null;
    }


    public ResponseEntity<String> addDepartments(EmployeeDepartment employeeDepartment) {

        employeeDepartmentRepository.save(employeeDepartment);
        return ResponseEntity.status(CREATED).body("department added successfully");

    }

    public ResponseEntity<String> addDesignations(EmployeeDesignation employeeDesignation) {

        employeeDesignationRepository.save(employeeDesignation);
        return ResponseEntity.status(CREATED).body("designation added successfully !");

    }
}
