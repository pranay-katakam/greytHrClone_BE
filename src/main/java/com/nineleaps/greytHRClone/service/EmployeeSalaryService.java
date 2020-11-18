package com.nineleaps.greytHRClone.service;

import com.nineleaps.greytHRClone.dto.EmployeeSalaryRequestDTO;
import com.nineleaps.greytHRClone.dto.SalaryDTO;
import com.nineleaps.greytHRClone.model.EmployeeLeave;
import com.nineleaps.greytHRClone.model.EmployeeSalary;
import com.nineleaps.greytHRClone.repository.EmployeeSalaryRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
public class EmployeeSalaryService {

    @Autowired
    EmployeeSalaryRepository employeeSalaryRepository;

    public ResponseEntity<String> addSalary(EmployeeSalaryRequestDTO employeeSalaryRequestDTO) {

        ModelMapper modelMapper = new ModelMapper();
        EmployeeSalary employeeSalary = modelMapper.map(employeeSalaryRequestDTO, EmployeeSalary.class);
        employeeSalaryRepository.save(employeeSalary);
        return ResponseEntity.status(HttpStatus.CREATED).body("Salary added successfully");
    }

    public ResponseEntity<List<SalaryDTO>> getSalaryDetails(int eid) {
        List<SalaryDTO> salaryDTOs = new ArrayList<>();

        List<EmployeeSalary> salaryDetails = employeeSalaryRepository.getSalaryDetails(eid);

        for (EmployeeSalary employeeSalary : salaryDetails) {
            LocalDateTime ToDate = (employeeSalary.getToDate() == null) ? LocalDateTime.now().minusMonths(1) : employeeSalary.getToDate();

            for (LocalDateTime date = employeeSalary.getFromDate(); date.isBefore(ToDate); date = date.plusMonths(1)) {
                LocalDateTime currentIterationpayDate = date;
                SalaryDTO salaryDTO = TotalSalaryDetails(employeeSalary.getTotalSalary(), employeeSalary.getEid(), currentIterationpayDate);
                salaryDTOs.add(salaryDTO);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(salaryDTOs);
    }

    //
    private SalaryDTO TotalSalaryDetails(int totalSalary, int eid, LocalDateTime payDate) {

//TODO lookup-table
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
        String StringDate = payDate.format(DateTimeFormatter.ofPattern("LLL yyyy"));

        SalaryDTO salaryDTO = new SalaryDTO();

        salaryDTO.setPayDate(StringDate);
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
        salaryDTO.setNetPay((int) netPay);

        return salaryDTO;
    }


}
