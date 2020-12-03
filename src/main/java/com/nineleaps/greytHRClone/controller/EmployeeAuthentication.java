package com.nineleaps.greytHRClone.controller;


import com.nineleaps.greytHRClone.dto.ApiResponseDTO;
import com.nineleaps.greytHRClone.dto.EmployeeRegistrationDTO;
import com.nineleaps.greytHRClone.dto.LoginDTO;
import com.nineleaps.greytHRClone.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@Tag(name = "employee authentication controller", description = "Controller class deals employee authentication")
@RestController
public class EmployeeAuthentication {

    @Autowired
    AuthenticationService authenticationService;

    @Operation(summary = "employee signup registration", description = "To add all the required details of employee", tags = {"Signup"})
    @PostMapping(path = "/register")
    public ResponseEntity<String> Signup(@RequestBody @Valid EmployeeRegistrationDTO employeeRegistrationDTO) {
        return authenticationService.Signup(employeeRegistrationDTO);
    }

    @Operation(summary = "user login", description = "To provide access to the user based on credentials", tags = {"Login"})
    @PostMapping(path = "/login")
    public ResponseEntity<ApiResponseDTO> Login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        return authenticationService.Login(loginDTO,response);
    }

    @Operation(summary = "user logout", description = "signout from the application", tags = {"Logout"})
    @GetMapping(path="/logout")
    public ResponseEntity<String> Logout(HttpServletRequest request, HttpServletResponse response){
        return authenticationService.Logout(request,response);
    }

}
