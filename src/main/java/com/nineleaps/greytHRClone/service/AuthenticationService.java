package com.nineleaps.greytHRClone.service;

import com.nineleaps.greytHRClone.exception.BadRequestException;
import com.nineleaps.greytHRClone.model.EmployeeData;
import com.nineleaps.greytHRClone.repository.EmployeeDataRepository;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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


    public ResponseEntity<JSONObject> Login(EmployeeData userCredentials, HttpServletResponse response) {
        try {

            int existByEmail = employeeDataRepository.exist(userCredentials.getEmail());
            if (existByEmail != 0) {

                String email = userCredentials.getEmail();
                String password = userCredentials.getPassword();
                JSONObject dbuser = employeeDataRepository.UserByEmail(email);

                String dbpassword = (String) dbuser.get("password");
                int id = (int) dbuser.get("emp_id");
                if (dbpassword.equals(password)) {
                    generateCookie(response, id);
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

    private void generateCookie(HttpServletResponse response, int id) {
        String Id = String.valueOf(id);
        ResponseCookie resCookie = ResponseCookie.from("userID", Id)
                .httpOnly(true)
                .sameSite("None")
                .secure(false)
                .path("/")
                .maxAge(Math.toIntExact(7 * 24 * 60 * 60)) // expires in 7 days
                .build();
        response.addHeader("Set-Cookie", resCookie.toString());

    }


    public ResponseEntity<String> Logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("userID", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.status(OK).body("Successfully logged out");

    }
}


