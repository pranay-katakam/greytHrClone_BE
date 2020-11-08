package com.nineleaps.greytHRClone.controller;

import com.nineleaps.greytHRClone.dto.*;

import com.nineleaps.greytHRClone.helper.FirebaseService;
import com.nineleaps.greytHRClone.model.CompanyLocation;
import com.nineleaps.greytHRClone.model.EmployeeDepartment;
import com.nineleaps.greytHRClone.model.EmployeeDesignation;
import com.nineleaps.greytHRClone.model.Role;
import com.nineleaps.greytHRClone.service.EmployeeDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import javax.ws.rs.Produces;
import java.util.List;

@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@Tag(name = "employee details controller", description = "Controller class deals employee details")
@RestController
public class EmployeeDetails {

    @Autowired
    private EmployeeDetailsService employeeDetailsService;

    @Operation(summary = "View Profile details", description = "To get the profile details of employee", tags = {"viewProfile"})
    @GetMapping(path = "/profile")
    public ResponseEntity<ProfileDTO> profile(@RequestAttribute("id") int id) {
        return employeeDetailsService.profile(id);
    }


    @Operation(summary = "Add new department ", description = "To add required departments", tags = {"addDepartment"})
    @PostMapping(path = "/department")
    public ResponseEntity<String> addDepartment(@RequestBody EmployeeDepartmentDTO employeeDepartmentDTO) {
        return employeeDetailsService.addDepartment(employeeDepartmentDTO);
    }

    @Operation(summary = "Add new designation ", description = "To add required designation", tags = {"addDesignation"})
    @PostMapping(path = "/designation")
    public ResponseEntity<String> addDesignation(@RequestBody EmployeeDesignationDTO employeeDesignationDTO){
        return employeeDetailsService.addDesignation(employeeDesignationDTO);
    }

    @Operation(summary = "View departments ", description = "To get available departments", tags = {"getDepartments"})
    @GetMapping(path = "/departments")
    @Produces({"application/json"})
    public ResponseEntity<List<EmployeeDepartment>> getDepartments() {
        return employeeDetailsService.getDepartments();
    }

    @Operation(summary = "View designations ", description = "To get available designations", tags = {"getDesignations"})
    @GetMapping(path = "/designations")
    public ResponseEntity<List<EmployeeDesignation>> getDesignations() {
        return employeeDetailsService.getDesignations();
    }

    @Operation(summary = "View employee to be assigned as manager ", description = "To get list of all employees can be assigned as manager", tags = {"getAssignableManagers"})
    @GetMapping(path = "/managers")
    public ResponseEntity<List<JSONObject>> getManagers() {
        return employeeDetailsService.getManagers();
    }


    @Operation(summary = "to assign a manager", description = "assign managers to an employee", tags = {"assignNewManager"})
    @PatchMapping(path = "/assignManager")
    public ResponseEntity<String> assignManager(@RequestParam(value = "mid") int mid, @RequestParam(value = "eid") int eid) {
        return employeeDetailsService.assignManagers(mid, eid);
    }

    @Operation(summary = "to upload image", description = "To upload images to the firebase", tags = {"uploadImage"})
    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, @RequestAttribute("id") int id) throws Exception {
        return employeeDetailsService.uploadFile(file, id);
    }


    @PostMapping("/company-location")
    public ResponseEntity<String> addCompanyLocation(@RequestBody CompanyLocationDTO companyLocationDTO){
        return employeeDetailsService.addCompanyLocation(companyLocationDTO);
    }

    @PostMapping("/role")
    public ResponseEntity<String> addRoles(@RequestBody RoleDTO roleDTO){
        return employeeDetailsService.addRoles(roleDTO);
    }

}
