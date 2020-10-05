package com.nineleaps.greytHRClone.controller;

import com.nineleaps.greytHRClone.dto.ProfileDTO;
import com.nineleaps.greytHRClone.model.EmployeeDepartment;
import com.nineleaps.greytHRClone.model.EmployeeDesignation;
import com.nineleaps.greytHRClone.service.EmployeeDetailsService;

//import io.swagger.annotations.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.graalvm.compiler.word.Word;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@Tag(name = "employee details controller", description = "Controller class deals employee details")
@RestController
public class EmployeeDetails {

    @Autowired
    EmployeeDetailsService employeeDetailsService;

    @Operation(summary = "View Profile details", description = "To get the profile details of employee", tags = { "viewProfile" })
    @GetMapping(path = "/profile")
    public ResponseEntity<ProfileDTO> profile(@RequestAttribute("id") int id) {
        return employeeDetailsService.profile(id);
    }


    @Operation(summary = "Add new department ", description = "To add required departments", tags = { "addDepartment" })
    @PostMapping(path = "/department")
    public ResponseEntity<String> addDepartment(@RequestBody EmployeeDepartment employeeDepartment) {
        return employeeDetailsService.addDepartment(employeeDepartment);
    }

    @Operation(summary = "Add new designation ", description = "To add required designation", tags = { "addDesignation" })
    @PostMapping(path = "/designation")

    public ResponseEntity<String> addDesignation(@Valid @RequestBody EmployeeDesignation employeeDesignation){
        return employeeDetailsService.addDesignation(employeeDesignation);
    }

    @Operation(summary = "View departments ", description = "To get available departments", tags = { "getDepartments" })
    @GetMapping(path = "/departments")
    public ResponseEntity<Iterable<EmployeeDepartment>> getDepartments() {
        return employeeDetailsService.getDepartments();
    }

    @Operation(summary = "View designations ", description = "To get available designations", tags = { "getDesignations" })
    @GetMapping(path = "/designations")
    public ResponseEntity<Iterable<EmployeeDesignation>> getDesignations() {
        return employeeDetailsService.getDesignations();
    }

    @Operation(summary = "View employee to be assigned as manager ", description = "To get list of all employees can be assigned as manager", tags = { "getAssignableManagers" })
    @GetMapping(path = "/managers")
    public ResponseEntity<List<JSONObject>> getManagers() {

        return employeeDetailsService.getManagers();
    }


    @Operation(summary = "to assign a manager", description = "assign managers to an employee", tags = { "assignNewManager" })
    @PatchMapping(path="/assignManager")
    public ResponseEntity<String> assignManager(@RequestParam(value = "mid") int mid,@RequestParam(value = "eid") int eid){
        return employeeDetailsService.assignManagers(mid,eid);
    }
}
