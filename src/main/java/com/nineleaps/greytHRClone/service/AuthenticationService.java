package com.nineleaps.greytHRClone.service;

import com.nineleaps.greytHRClone.model.EmployeeData;
import com.nineleaps.greytHRClone.repository.EmployeeDataRepository;

//import org.json.JSONObject;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.Date;

import static org.springframework.http.HttpStatus.*;


@Service
public class AuthenticationService {
    @Autowired
    EmployeeDataRepository employeeDataRepository;

    public ResponseEntity<String> Signup(EmployeeData employeeData) {
        employeeDataRepository.save(employeeData);
        return ResponseEntity.status(CREATED).body("user has been added successfully");
    }

    public ResponseEntity<JSONObject> profile(int id) {
        return ResponseEntity.status(OK).body(employeeDataRepository.profile(id));
    }

    public ResponseEntity<String> Login(EmployeeData userCredentials) {
        try {
            int existByEmail = employeeDataRepository.exist(userCredentials.getEmail());
            if (existByEmail != 0) {
                String email = userCredentials.getEmail();
                String password = userCredentials.getPassword();

                JSONObject dbuser = employeeDataRepository.UserByEmail(email);
                System.out.println(dbuser);

                String dbpassword = (String) dbuser.get("password");

                String loginResponse = "";
                if (dbpassword.equals(password)) {
                    loginResponse = "Login successful";
                } else {
                    loginResponse = "wrong credentials";
                }
                return ResponseEntity.status(OK).body(loginResponse);
            } else {
                return ResponseEntity.status(NOT_FOUND).body("please enter a valid email");
            }
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body("caught in catch");
        }

    }
}
