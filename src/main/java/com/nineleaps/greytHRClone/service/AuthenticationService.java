package com.nineleaps.greytHRClone.service;

import com.nineleaps.greytHRClone.model.EmployeeData;
import com.nineleaps.greytHRClone.repository.EmployeeDataRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    @Autowired
    EmployeeDataRepository employeeDataRepository;


    public String Signup(EmployeeData employeeData){
        employeeDataRepository.save(employeeData);
        return "user has been added successfully";
    }


    public EmployeeData profile(int id){

        return  employeeDataRepository.findById(id).orElseThrow(() -> new NullPointerException());

    }
}
