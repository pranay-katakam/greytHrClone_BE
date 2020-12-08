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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
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


    public ResponseEntity<AnnualEarningsDTO> getSalaryDetails(int eid, Year year) {
        AnnualEarningsDTO annualEarningsDTO = new AnnualEarningsDTO();
        LocalDate financialYearFrom = year.atMonth(4).atDay(1);
        LocalDate financialYearTo = year.atMonth(3).atDay(31).plusYears(1);

        List<SalaryDTO> preSalaryDTOs = new ArrayList<>();
        List<SalaryDTO> postSalaryDTOs = new ArrayList<>();
        int annualGrossPay = 0;
        int annualDeduction = 0;
        int annualNetPay = 0;
        List<EmployeeSalary> salaryDetails = employeeSalaryRepository.getSalaryDetails(eid);

        for (EmployeeSalary employeeSalary : salaryDetails) {
            LocalDate ToDate = (employeeSalary.getToDate() == null) ? LocalDate.now().minusMonths(1) : employeeSalary.getToDate();
            for (LocalDate date = employeeSalary.getFromDate(); date.isBefore(ToDate); date = date.plusMonths(1)) {
                SalaryDTO salaryDTO = getDateSalary(employeeSalary, date);
                preSalaryDTOs.add(salaryDTO);
            }
        }

        for (LocalDate date = financialYearFrom; date.isBefore(financialYearTo); date = date.plusMonths(1)) {
            YearMonth finalDate = YearMonth.from(date);
            int salary = preSalaryDTOs.stream()
                    .filter(s -> s.getPayDate().equals(finalDate))
                    .map(SalaryDTO::getNetPay)
                    .findAny()
                    .orElse(0);

            SalaryDTO salaryDTO = TotalSalaryDetails(salary, date);
            postSalaryDTOs.add(salaryDTO);
            annualGrossPay = salaryDTO.getTotalEarning() + annualGrossPay;
            annualDeduction = salaryDTO.getTotalDeduction() + annualDeduction;
            annualNetPay = salaryDTO.getNetPay() + annualNetPay;
        }

        annualEarningsDTO.setAnnualGrossPay(annualGrossPay);
        annualEarningsDTO.setAnnualNetDeduction(annualDeduction);
        annualEarningsDTO.setAnnualNetPay(annualNetPay);
        annualEarningsDTO.setYearlySalary(postSalaryDTOs);
        return ResponseEntity.status(HttpStatus.OK).body(annualEarningsDTO);
    }


    private SalaryDTO getDateSalary(EmployeeSalary salaryDetails, LocalDate date) {
        SalaryDTO salaryDTO = new SalaryDTO();
        salaryDTO.setNetPay(salaryDetails.getTotalSalary());
        salaryDTO.setPayDate(YearMonth.from(date));
        return salaryDTO;
    }

    //
    private SalaryDTO TotalSalaryDetails(int totalSalary, LocalDate payDate) {
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

        salaryDTO.setPayDate(YearMonth.from(payDate));
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


    public ResponseEntity<SalaryDTO> getSalary(int eid, Optional<YearMonth> yearMonth) {
        YearMonth yearMonthValue = yearMonth.orElse(YearMonth.now().minusMonths(1));
        LocalDate date = yearMonthValue.atDay(1);//convert YearMonth to LocalDate with day 1

        List<EmployeeSalary> salaries = employeeSalaryRepository.findByEidOrderBySidDesc(eid);
        int salary = salaries.stream()
                .filter(s -> s.getFromDate().isBefore(date) || s.getFromDate().equals(date))
                .findFirst()
                .map(EmployeeSalary::getTotalSalary)
                .orElse(0);
        System.out.println("salary " + salary);
        SalaryDTO salaryDTO = salary == 0 ? new SalaryDTO() : TotalSalaryDetails(salary, date);
        return ResponseEntity.status(HttpStatus.OK).body(salaryDTO);

    }
}
