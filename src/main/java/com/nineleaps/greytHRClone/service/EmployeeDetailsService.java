package com.nineleaps.greytHRClone.service;

import com.nineleaps.greytHRClone.dto.EventDTO;

import com.nineleaps.greytHRClone.dto.ProfileDTO;
import com.nineleaps.greytHRClone.exception.BadRequestException;
import com.nineleaps.greytHRClone.model.EmployeeData;

import com.nineleaps.greytHRClone.model.EmployeeDepartment;
import com.nineleaps.greytHRClone.model.EmployeeDesignation;
import com.nineleaps.greytHRClone.repository.EmployeeDataRepository;
import com.nineleaps.greytHRClone.repository.EmployeeDepartmentRepository;
import com.nineleaps.greytHRClone.repository.EmployeeDesignationRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.http.HttpStatus.OK;

@Service
public class EmployeeDetailsService {

    private EmployeeDataRepository employeeDataRepository;

    private EmployeeDepartmentRepository employeeDepartmentRepository;
    private EmployeeDesignationRepository employeeDesignationRepository;

    @Autowired
    public EmployeeDetailsService(EmployeeDataRepository employeeDataRepository, EmployeeDepartmentRepository employeeDepartmentRepository, EmployeeDesignationRepository employeeDesignationRepository) {
        this.employeeDataRepository = employeeDataRepository;
        this.employeeDepartmentRepository = employeeDepartmentRepository;
        this.employeeDesignationRepository = employeeDesignationRepository;
    }

    public ResponseEntity<ProfileDTO> profile(int id) {
        try {
            JSONObject dbprofile = employeeDataRepository.profile(id);
            int mangerId = (int) dbprofile.get("manager_id");
            ProfileDTO profileDTO = new ProfileDTO();
            profileDTO.setName((String) dbprofile.get("name"));
            profileDTO.setDepartment((String) dbprofile.get("department"));
            profileDTO.setDesignation((String) dbprofile.get("designation"));
            profileDTO.setManagerId(mangerId);
            profileDTO.setLocation((String) dbprofile.get("location"));
            String managerName = "not Assigned";
            if (mangerId != 0) {
                managerName = employeeDataRepository.getManagerName(mangerId);
            }
            profileDTO.setManagerName(managerName);
            return ResponseEntity.status(OK).body(profileDTO);
        } catch (Exception e) {
            throw new BadRequestException("Please enter valid id");
        }
    }


    public ResponseEntity<List<EventDTO>> events() {

        List<JSONObject> birthdayList = employeeDataRepository.BirthdayList();
        List<JSONObject> anniversaryList = employeeDataRepository.AnniversaryList();


        List<EventDTO> eventDTOS = new ArrayList<>();

        for (JSONObject bDay : birthdayList) {
            EventDTO eventDTO = new EventDTO();
            eventDTO.setName((String) bDay.get("name"));
            eventDTO.setEventType(EventDTO.EventType.BIRTHDAY);
            eventDTO.setDate((Date) bDay.get("dob"));
            eventDTOS.add(eventDTO);
        }


        for (JSONObject anniversary : anniversaryList) {
            EventDTO eventDTO = new EventDTO();
            eventDTO.setName((String) anniversary.get("name"));
            eventDTO.setEventType(EventDTO.EventType.ANNIVERSARY);
            eventDTO.setDate((Date) anniversary.get("created_date"));
            eventDTOS.add(eventDTO);
        }


//        eventDTOS.sort(Comparator.comparing(EventDTO::getDate));
//          eventDTOS.stream().sorted(comparing(EventDTO::getDate).reversed());
//        eventDTOS.sort(comparing(EventDTO::getDate).reversed());/////////

        return ResponseEntity.status(HttpStatus.OK).body(eventDTOS);
    }

//    public ResponseEntity<JSONObject> profile(int id) {
//        return ResponseEntity.status(OK).body(employeeDataRepository.profile(id));
//    }

    public ResponseEntity<String> addDepartment(EmployeeDepartment employeeDepartment) {
        employeeDepartmentRepository.save(employeeDepartment);
        return ResponseEntity.status(HttpStatus.CREATED).body("department added successfully");
    }

    public ResponseEntity<String> addDesignation(EmployeeDesignation employeeDesignation) {
        employeeDesignationRepository.save(employeeDesignation);
        return ResponseEntity.status(HttpStatus.CREATED).body("designation added successfully");
    }

    public ResponseEntity<Iterable<EmployeeDepartment>> getDepartments() {
        return ResponseEntity.status(HttpStatus.OK).body(employeeDepartmentRepository.findAll());
    }

    public ResponseEntity<Iterable<EmployeeDesignation>> getDesignations() {
        return ResponseEntity.status(HttpStatus.OK).body(employeeDesignationRepository.findAll());

    }

    public ResponseEntity<List<JSONObject>> getManagers() {
        return ResponseEntity.status(HttpStatus.OK).body(employeeDataRepository.getAllEmployee());
    }
}
