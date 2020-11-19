package com.nineleaps.greytHRClone.service;

import com.nineleaps.greytHRClone.dto.AnnualEarningsDTO;
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
import java.util.stream.Stream;


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

    public ResponseEntity<AnnualEarningsDTO> getSalaryDetails(int eid) {
        AnnualEarningsDTO annualEarningsDTO = new AnnualEarningsDTO();
        List<SalaryDTO> salaryDTOs = new ArrayList<>();
        int annualGrossPay = 0;
        int annualDeduction = 0;
        int annualNetPay = 0;
        List<EmployeeSalary> salaryDetails = employeeSalaryRepository.getSalaryDetails(eid);

        for (EmployeeSalary employeeSalary : salaryDetails) {
            LocalDateTime ToDate = (employeeSalary.getToDate() == null) ? LocalDateTime.now().minusMonths(1) : employeeSalary.getToDate();

            for (LocalDateTime date = employeeSalary.getFromDate(); date.isBefore(ToDate); date = date.plusMonths(1)) {
                LocalDateTime currentIterationpayDate = date;
                SalaryDTO salaryDTO = TotalSalaryDetails(employeeSalary.getTotalSalary(), employeeSalary.getEid(), currentIterationpayDate);
                salaryDTOs.add(salaryDTO);
                annualGrossPay = salaryDTO.getTotalEarning() + annualGrossPay;
                annualDeduction = salaryDTO.getTotalDeduction() + annualDeduction;
                annualNetPay = salaryDTO.getNetPay() + annualNetPay;
            }

        }
        annualEarningsDTO.setAnnualGrossPay(annualGrossPay);
        annualEarningsDTO.setAnnualNetDeduction(annualDeduction);
        annualEarningsDTO.setAnnualNetPay(annualNetPay);

        long count = salaryDTOs.stream().count();
        Stream<SalaryDTO> stream = salaryDTOs.stream();
        SalaryDTO currentMonthSalary = stream.skip(count - 1).findFirst().get();

        annualEarningsDTO.setCurrentMonthSalary(currentMonthSalary);

        annualEarningsDTO.setYearlySalary(salaryDTOs);


        return ResponseEntity.status(HttpStatus.OK).body(annualEarningsDTO);
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
