package com.nineleaps.greytHRClone.service;

import com.nineleaps.greytHRClone.dto.ApiResponseDTO;
import com.nineleaps.greytHRClone.dto.EmployeeRegistrationDTO;
import com.nineleaps.greytHRClone.dto.LoginDTO;
import com.nineleaps.greytHRClone.exception.BadRequestException;
import com.nineleaps.greytHRClone.helper.MailContentBuilder;
import com.nineleaps.greytHRClone.model.*;
import com.nineleaps.greytHRClone.repository.EmployeeDataRepository;

import com.nineleaps.greytHRClone.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.util.*;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@Service
public class AuthenticationService {

    private EmployeeDataRepository employeeDataRepository;
    private MailContentBuilder mailContentBuilder;
    private RoleRepository roleRepository;

    @Autowired
    public AuthenticationService(EmployeeDataRepository employeeDataRepository,MailContentBuilder mailContentBuilder,RoleRepository roleRepository) {
        this.employeeDataRepository = employeeDataRepository;
        this.mailContentBuilder = mailContentBuilder;
        this.roleRepository=roleRepository;

    }

//    @Override
//    public UserDto signup(UserDto userDto) {
//        Role userRole;
//        User user = userRepository.findByEmail(userDto.getEmail());
//        if (user == null) {
//            if (userDto.isAdmin()) {
//                userRole = roleRepository.findByRole(UserRoles.ADMIN);
//            } else {
//                userRole = roleRepository.findByRole(UserRoles.PASSENGER);
//            }
//            user = new User()
//                    .setEmail(userDto.getEmail())
//                    .setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()))
//                    .setRoles(new HashSet<>(Arrays.asList(userRole)))
//                    .setFirstName(userDto.getFirstName())
//                    .setLastName(userDto.getLastName())
//                    .setMobileNumber(userDto.getMobileNumber());
//            return UserMapper.toUserDto(userRepository.save(user));
//        }

    public ResponseEntity<String> Signup(EmployeeRegistrationDTO employeeRegistrationDTO) {
        ResponseEntity<String> responseEntity;
        Role userRole  = roleRepository.findByRole(UserRoles.USER);
        int existByEmail = employeeDataRepository.exist(employeeRegistrationDTO.getEmail());

        if (existByEmail != 0) {
            responseEntity = ResponseEntity.status(BAD_REQUEST).body("User Already Exists !!");
        }
        else {

            String name= StringUtils.capitalize(employeeRegistrationDTO.getName());//capitalize the first letter
            CompanyLocation location=new CompanyLocation();
            location.setLocationId(employeeRegistrationDTO.getLocationId());//changing employeeRegistrationDTO.getLocationId() to type CompanyLocation
            EmployeeDesignation designation=new EmployeeDesignation();
            designation.setDesId(employeeRegistrationDTO.getDesignationId());//changing employeeRegistrationDTO getDesignationId to type EmployeeDesignation
            Set<EmployeeDepartment> employeeDepartments = new HashSet<>();
            for (Integer departmentId:employeeRegistrationDTO.getDepartmentId()){
                EmployeeDepartment employeeDepartment=new EmployeeDepartment();
                employeeDepartment.setDepId(departmentId);
                employeeDepartments.add(employeeDepartment);
            }

            EmployeeData employeeData=new EmployeeData();
            employeeData.setName(name);
            employeeData.setEmail(employeeRegistrationDTO.getEmail());
            employeeData.setPassword(employeeRegistrationDTO.getPassword());
            employeeData.setDob(employeeRegistrationDTO.getDob());
            employeeData.setLocation(location);
            employeeData.setGender(employeeRegistrationDTO.getGender());
            employeeData.setContactNumber(employeeRegistrationDTO.getContactNumber());
            employeeData.setManagerId(employeeRegistrationDTO.getManagerId());
            employeeData.setDepartments( employeeDepartments);
            employeeData.setDesignation(designation);
            employeeData.setRoles(new HashSet<>(Arrays.asList(userRole)));
            employeeDataRepository.save(employeeData);
           // mailContentBuilder.sendWelcomeMail();
            responseEntity = ResponseEntity.status(CREATED).body("Signed up successfully !!");
        }
        return responseEntity;
    }

    public ResponseEntity<ApiResponseDTO> Login(LoginDTO loginDTO, HttpServletResponse response) {
        try {
            EmployeeData employeeData = Optional.ofNullable(employeeDataRepository.findByEmail(loginDTO.getEmail())).orElseThrow(()->new BadRequestException("email doesn't exist please signUp "));
            if(employeeData.getPassword().equals(loginDTO.getPassword())){
                generateCoookie(response, employeeData.getEmpId());
                return ResponseEntity.status(OK).body(new ApiResponseDTO("Login Successful"));
            }else {
                throw new BadRequestException("Incorrect password\nType correct password");
            }
        }
        catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    private void generateCoookie(HttpServletResponse response, int id) {
        String cookieValue = String.valueOf(id);
        int timeOfExpire=(1 * 24 * 60 * 60); // expires in 1 day
        ResponseCookie resCookie = ResponseCookie.from("userID", cookieValue)
                .httpOnly(true)
                .sameSite("None")
                .secure(false)
                .path("/")
                .maxAge(Math.toIntExact(timeOfExpire))
                .build();
        response.addHeader("Set-Cookie", resCookie.toString());
    }

    public ResponseEntity<String> Logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = WebUtils.getCookie(request, "userID");
        if (cookie != null) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return ResponseEntity.status(OK).body("User Successfully Logged Out");

        } else {
            return ResponseEntity.status(HttpServletResponse.SC_REQUEST_TIMEOUT).body("Session Expired");

        }
    }
}


