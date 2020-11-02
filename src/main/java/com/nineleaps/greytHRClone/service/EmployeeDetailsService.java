package com.nineleaps.greytHRClone.service;

import com.nineleaps.greytHRClone.dto.ProfileDTO;
import com.nineleaps.greytHRClone.exception.BadRequestException;
import com.nineleaps.greytHRClone.exception.UnsupportedMediaTypeException;
import com.nineleaps.greytHRClone.helper.FirebaseService;
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

import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.nineleaps.greytHRClone.common.Constants.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Service
public class EmployeeDetailsService {

    private EmployeeDataRepository employeeDataRepository;
    private EmployeeDepartmentRepository employeeDepartmentRepository;
    private EmployeeDesignationRepository employeeDesignationRepository;
    private FirebaseService firebaseService;

    @Autowired
    public EmployeeDetailsService(EmployeeDataRepository employeeDataRepository, EmployeeDepartmentRepository employeeDepartmentRepository, EmployeeDesignationRepository employeeDesignationRepository, FirebaseService firebaseService) {
        this.employeeDataRepository = employeeDataRepository;
        this.employeeDepartmentRepository = employeeDepartmentRepository;
        this.employeeDesignationRepository = employeeDesignationRepository;
        this.firebaseService = firebaseService;
    }


    public ResponseEntity<ProfileDTO> profile(int id) {
        try {
            EmployeeData dbprofile = employeeDataRepository.findById(id).orElseThrow(() -> new BadRequestException("Invalid Id"));
            ;
            int mangerId = dbprofile.getManagerId();
            int empId = dbprofile.getEmpId();
            ProfileDTO profileDTO = new ProfileDTO();
            profileDTO.setName(dbprofile.getName());

            List<String> departmentList = new ArrayList<>();//initialising a new list

            for (EmployeeDepartment empD : dbprofile.getDepartments()) {
                departmentList.add(empD.getDepartment());
            }
            profileDTO.setDepartment(departmentList);
            profileDTO.setDesignation(dbprofile.getDesignation().getDesignation());
            profileDTO.setManagerId(mangerId);
            profileDTO.setLocation(dbprofile.getLocation());
            profileDTO.setEid(empId);
            System.out.println(dbprofile.getImageName());
            if (dbprofile.getImageName() != null) {
                profileDTO.setImageName(FIREBASE_URL_PREFIX + dbprofile.getImageName() + FIREBASE_URL_SUFFIX);
            }
            String managerName = "not Assigned";
            if (mangerId != 0) {
                managerName = employeeDataRepository.getManagerName(mangerId);
            }
            profileDTO.setManagerName(managerName);
            return ResponseEntity.status(OK).body(profileDTO);

        } catch (Exception e) {
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


    public ResponseEntity<String> updateName(String name, int eid) {
        employeeDataRepository.updateName(name, eid);
        return ResponseEntity.status(HttpStatus.CREATED).body("Updated successfully");
    }


    public ResponseEntity<String> assignManagers(int mid, int eid) {
        employeeDataRepository.assignManager(mid, eid);
        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully assigned manager");

    }


    public ResponseEntity<String> uploadFile(MultipartFile file, int id) throws Exception {
        System.out.println("size " + IsImageSize1MB(file.getSize()));
        System.out.println("format" + checkForImageFormat(file.getContentType()));
        if (IsImageSize1MB(file.getSize()) && checkForImageFormat(file.getContentType())) {
            String ImageName = firebaseService.uploadFile(file);
            employeeDataRepository.saveImageById(ImageName, id);
            return ResponseEntity.status(CREATED).body("image uploaded successsfully");
        } else {
            throw new UnsupportedMediaTypeException("please upload  image Inside 1 MB and of type .png/jpg/jpeg/.gif ");
        }
    }

    // confirms whether file is in image format only
    private boolean checkForImageFormat(String FileName) {
        Pattern p = Pattern.compile(IMG_REGEX);
        Matcher m = p.matcher(FileName);
        return m.matches();
    }

    private boolean IsImageSize1MB(long fileSize) {
        if (fileSize <= 1048576) {
            return true;
        } else {
            return false;
        }
    }



}
