package com.nineleaps.greytHRClone.service;

import com.nineleaps.greytHRClone.exception.BadRequestException;
import com.nineleaps.greytHRClone.exception.DataAlreadyExistsException;
import com.nineleaps.greytHRClone.model.EmployeeData;
import com.nineleaps.greytHRClone.repository.EmployeeDataRepository;

//import org.json.JSONObject;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;


@Service
public class AuthenticationService {
    @Autowired
    EmployeeDataRepository employeeDataRepository;

    public ResponseEntity<String> Signup(EmployeeData employeeData) {
        ResponseEntity<String> responseEntity;


        int existByEmail = employeeDataRepository.exist(employeeData.getEmail());

        if (existByEmail != 0) {
            responseEntity = ResponseEntity.status(BAD_REQUEST).body("User Already Exists !!");
        } else {
            responseEntity = ResponseEntity.status(OK).body("Signed up successfully !!");
            employeeDataRepository.save(employeeData);
        }
        return responseEntity;
    }


    public ResponseEntity<JSONObject> Login(EmployeeData userCredentials) {
        try {
            int existByEmail = employeeDataRepository.exist(userCredentials.getEmail());
            if (existByEmail != 0) {
                String email = userCredentials.getEmail();
                String password = userCredentials.getPassword();
                JSONObject dbuser = employeeDataRepository.UserByEmail(email);

                String dbpassword = (String) dbuser.get("password");

                if (dbpassword.equals(password)) {
                    JSONObject responseMsg = new JSONObject();
                    responseMsg.put("message", "Login Successful");
                    return ResponseEntity.status(OK).body(responseMsg);
                } else {
//                    return ResponseEntity.status(UNAUTHORIZED).body("Invalid credentials");
                   throw new BadRequestException("wrong password");
                }

            } else {
                throw new BadRequestException("please enter a valid name");
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }
}
