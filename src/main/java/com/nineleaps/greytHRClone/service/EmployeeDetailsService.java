package com.nineleaps.greytHRClone.service;

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
            EmployeeData dbprofile = employeeDataRepository.findById(id).orElseThrow(() -> new BadRequestException("Invalid Id"));;
            int mangerId = dbprofile.getManagerId();
            int empId =dbprofile.getEmpId();
            ProfileDTO profileDTO = new ProfileDTO();
            profileDTO.setName(dbprofile.getName());

            List<String> departmentList=new ArrayList<>();//initialising a new list

            for(EmployeeDepartment empD:dbprofile.getDepartments()){
                departmentList.add(empD.getDepartment());
            }
            profileDTO.setDepartment(departmentList);
            profileDTO.setDesignation(dbprofile.getDesignation().getDesignation());
            profileDTO.setManagerId(mangerId);
            profileDTO.setLocation(dbprofile.getLocation());
            profileDTO.setEid(empId);

            String managerName = "not Assigned";
            if (mangerId != 0) {
                managerName = employeeDataRepository.getManagerName(mangerId);
            }
            profileDTO.setManagerName(managerName);
            return ResponseEntity.status(OK).body(profileDTO);

        }
        catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

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


    public ResponseEntity<String> updateName(String name,int eid) {
        employeeDataRepository.updateName(name,eid);
        return ResponseEntity.status(HttpStatus.CREATED).body("Updated successfully");
    }


    public ResponseEntity<String> assignManagers(int mid, int eid) {
        employeeDataRepository.assignManager(mid, eid);
        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully assigned manager");

    }
}
