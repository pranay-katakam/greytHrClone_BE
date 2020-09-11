package com.nineleaps.greytHRClone.service;

import com.nineleaps.greytHRClone.model.EmployeeData;
import com.nineleaps.greytHRClone.repository.EmployeeDataRepository;

//import org.json.JSONObject;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;


@Service
public class AuthenticationService {
    @Autowired
    EmployeeDataRepository employeeDataRepository;

    public ResponseEntity<String> Signup(EmployeeData employeeData){

        employeeDataRepository.save(employeeData);
        return ResponseEntity.status(CREATED).body("user has been added successfully");

    }


    public ResponseEntity<JSONObject> profile(int id){

        return ResponseEntity.status(OK).body(employeeDataRepository.profile(id));

    }

    public ResponseEntity<String> Login(EmployeeData userCredentials){
        String name = userCredentials.getName();
        String password = userCredentials.getPassword();

        EmployeeData dbuser = employeeDataRepository.findByName(name);


        String dbpassword = dbuser.getPassword();
        System.out.println(dbpassword + "PRINITING PASSWORD" + password);
        String loginResponse="";
        if(dbpassword.equals(password)){
            loginResponse= "Login successful";
        }
        else{
            loginResponse="wrong credentials";
        }
        return ResponseEntity.status(OK).body(loginResponse);

    }
}
