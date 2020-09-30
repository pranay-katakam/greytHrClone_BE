package com.nineleaps.greytHRClone.service;

import com.nineleaps.greytHRClone.dto.SalaryDTO;
import com.nineleaps.greytHRClone.model.EmployeeSalary;
import com.nineleaps.greytHRClone.repository.EmployeeSalaryRepository;
import org.json.simple.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class EmployeeSalaryService {

    @Autowired
    EmployeeSalaryRepository employeeSalaryRepository;

    public ResponseEntity<String> addSalary(EmployeeSalary employeeSalary) {
        System.out.println(employeeSalary);
        employeeSalaryRepository.save(employeeSalary);
        return ResponseEntity.status(HttpStatus.CREATED).body("Salary added successfully");

    }


    public ResponseEntity<List<SalaryDTO>> getSalaryDetails(int eid) {

        List<JSONObject> salaryDetails = employeeSalaryRepository.getSalaryDetails(eid);

        List<SalaryDTO> salaryDTOs = new ArrayList<>();

        for (JSONObject salaryDetail : salaryDetails) {

            int totalSalary = (int) salaryDetail.get("total_salary");
            double basic = totalSalary * .45;
            double hra = totalSalary * .18;
            double specialAllowance = totalSalary * .30037;
            double totalEarning = (basic + hra + specialAllowance);

            int pf = 1800;
            int profTax = 200;
            int groupMedicalDeduction = 321;
            int groupPolicyAccidentDeduction = 18;
            int totalDeduction = (pf + profTax + groupMedicalDeduction + groupPolicyAccidentDeduction);

            double netPay = (totalEarning - totalDeduction);

            SalaryDTO salaryDTO = new SalaryDTO();

            salaryDTO.setEid(eid);
            salaryDTO.setBasic((int) basic);
            salaryDTO.setHra((int) hra);
            salaryDTO.setSpecialAllowance((int) specialAllowance);
            salaryDTO.setPf(pf);
            salaryDTO.setProfTax(profTax);
            salaryDTO.setGroupMedicalDeduction(groupMedicalDeduction);
            salaryDTO.setGroupPolicyAccidentDeduction(groupPolicyAccidentDeduction);
            salaryDTO.setTotalEarning((int) totalEarning);
            salaryDTO.setTotalDeduction(totalDeduction);
            salaryDTO.setNetPay((int)netPay);


            salaryDTOs.add(salaryDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(salaryDTOs);

    }

}
