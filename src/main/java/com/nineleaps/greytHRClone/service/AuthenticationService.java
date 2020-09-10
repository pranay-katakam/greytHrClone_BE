package com.nineleaps.greytHRClone.service;

import com.nineleaps.greytHRClone.model.EmployeeData;
import com.nineleaps.greytHRClone.repository.EmployeeDataRepository;
//import org.json.JSONObject;
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

    public String Login(EmployeeData userCredentials){
        String name = userCredentials.getName();
        String password = userCredentials.getPassword();

        EmployeeData dbuser = employeeDataRepository.findByName(name);

        String dbpassword = dbuser.getPassword();
        System.out.println(dbpassword + "PRINITING PASSWORD" + password);
        if(dbpassword.equals(password)){
            return "Login successful";
        }
        else{
            return "wrong credentials";
        }
    }
}
