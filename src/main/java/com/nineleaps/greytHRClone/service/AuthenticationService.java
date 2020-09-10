package com.nineleaps.greytHRClone.service;

import com.nineleaps.greytHRClone.model.EmployeeData;
import com.nineleaps.greytHRClone.repository.EmployeeDataRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class AuthenticationService {
    @Autowired
    EmployeeDataRepository employeeDataRepository;


    public String Signup(EmployeeData employeeData){

        employeeDataRepository.save(employeeData);
        return "user has been added successfully";
    }


    public JSONObject profile(int id){

        return  employeeDataRepository.profile(id);

    }
}
