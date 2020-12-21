package com.nineleaps.greytHRClone.service;

import com.nineleaps.greytHRClone.dto.AnnualEarningsDTO;
import com.nineleaps.greytHRClone.dto.EmployeeSalaryRequestDTO;
import com.nineleaps.greytHRClone.dto.SalaryDTO;
import com.nineleaps.greytHRClone.model.EmployeeSalary;
import com.nineleaps.greytHRClone.repository.EmployeeDataRepository;
import com.nineleaps.greytHRClone.repository.EmployeeSalaryRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

import static com.nineleaps.greytHRClone.common.Constants.*;


@Service
public class EmployeeSalaryService {


    private EmployeeSalaryRepository employeeSalaryRepository;
    private EmployeeDataRepository employeeDataRepository;

    @Autowired
    public EmployeeSalaryService(EmployeeSalaryRepository employeeSalaryRepository, EmployeeDataRepository employeeDataRepository) {
        this.employeeSalaryRepository = employeeSalaryRepository;
        this.employeeDataRepository = employeeDataRepository;
    }

    public ResponseEntity<String> addSalary(EmployeeSalaryRequestDTO employeeSalaryRequestDTO) {

        ModelMapper modelMapper = new ModelMapper();
        EmployeeSalary employeeSalary = modelMapper.map(employeeSalaryRequestDTO, EmployeeSalary.class);
        employeeSalaryRepository.save(employeeSalary);
        return ResponseEntity.status(HttpStatus.CREATED).body("Salary added successfully");
    }


    public ResponseEntity<AnnualEarningsDTO> getSalaryDetails(int eid, Year year) {
        LocalDate userJoinDate= employeeDataRepository.findcreatedDateById(eid);
        AnnualEarningsDTO annualEarningsDTO = new AnnualEarningsDTO();
        LocalDate financialYearFrom = year.atMonth(4).atDay(1);
        LocalDate financialYearTo = year.atMonth(3).atDay(31).plusYears(1);

        List<SalaryDTO> preSalaryDTOs = new ArrayList<>();
        List<SalaryDTO> postSalaryDTOs = new ArrayList<>();
        int annualGrossPay = 0;
        int annualDeduction = 0;
        int annualNetPay = 0;
        int annualPf=0;
        List<EmployeeSalary> salaryDetails = employeeSalaryRepository.getSalaryDetails(eid);
        List<EmployeeSalary> yearMatchingSalaryDetailsData;
        List<EmployeeSalary> lowerBoundSalaryDetailsData;
        Year tempYear=year;
        do {
             yearMatchingSalaryDetailsData = yearMatchingSalaryDetails(tempYear, salaryDetails,"upperBound");
            tempYear=tempYear.minusYears(1);
        }while (yearMatchingSalaryDetailsData.isEmpty()&&userJoinDate.getYear()<tempYear.getValue()) ;

        yearMatchingSalaryDetailsData.addAll(yearMatchingSalaryDetails(year, salaryDetails,"lowerBound"));
        tempYear=year.plusYears(1);

        do {
            lowerBoundSalaryDetailsData=yearMatchingSalaryDetails(tempYear, salaryDetails,"lowerBound");
            tempYear=tempYear.plusYears(1);
        }while (lowerBoundSalaryDetailsData.isEmpty()&&!Year.now().isBefore(tempYear.minusYears(1))) ;

        yearMatchingSalaryDetailsData.addAll(lowerBoundSalaryDetailsData);

        for (EmployeeSalary employeeSalary : yearMatchingSalaryDetailsData) {
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
            if(salary!=0) {
                SalaryDTO salaryDTO = TotalSalaryDetails(salary, date);
                postSalaryDTOs.add(salaryDTO);
                annualGrossPay = salaryDTO.getTotalEarning() + annualGrossPay;
                annualDeduction = salaryDTO.getTotalDeduction() + annualDeduction;
                annualNetPay = salaryDTO.getNetPay() + annualNetPay;
                annualPf = salaryDTO.getPf() + annualPf;
            }
        }

        annualEarningsDTO.setAnnualGrossPay(annualGrossPay);
        annualEarningsDTO.setAnnualNetDeduction(annualDeduction);
        annualEarningsDTO.setAnnualNetPay(annualNetPay);
        annualEarningsDTO.setAnnualPf(annualPf);
        annualEarningsDTO.setYearlySalary(postSalaryDTOs);
        return ResponseEntity.status(HttpStatus.OK).body(annualEarningsDTO);
    }




    private List<EmployeeSalary> yearMatchingSalaryDetails(Year year,List<EmployeeSalary> salaryDetails,String type) {
        List<EmployeeSalary> salaries = new ArrayList<>();
        if (type.equals("upperBound")) {
            salaries = salaryDetails.stream()
                    .filter(s -> s.getFromDate().getYear() == year.getValue())
                    .collect(Collectors.toList());
        } else {
            salaries = salaryDetails.stream()
                    .filter(s -> ((s.getToDate()==null)?Year.now().getValue():s.getToDate().getYear()) == year.getValue())
                    .collect(Collectors.toList());
        }
        return salaries;
    }



    private SalaryDTO getDateSalary(EmployeeSalary salaryDetails, LocalDate date) {
        SalaryDTO salaryDTO = new SalaryDTO();
        salaryDTO.setNetPay(salaryDetails.getTotalSalary());
        salaryDTO.setPayDate(YearMonth.from(date));
        return salaryDTO;
    }


    private SalaryDTO TotalSalaryDetails(int totalSalary, LocalDate payDate) {
        double basic = totalSalary * BASIC;
        double hra = totalSalary * HRA;
        double specialAllowance = totalSalary * SPECIAL_ALLOWANCE;
        double pf =totalSalary* PF;
        double profTax = totalSalary * PROF_TAX;
        double groupMedicalDeduction = totalSalary * GROUP_MEDICAL_DEDUCTION;
        double groupPolicyAccidentDeduction = totalSalary * GROUP_POLICY_ACCIDENT_DEDUCTION;
        double totalDeduction = (pf + profTax + groupMedicalDeduction + groupPolicyAccidentDeduction);
        double netPay = (totalSalary - totalDeduction);
//        String StringDate = payDate.format(DateTimeFormatter.ofPattern("LLL yyyy"));
        SalaryDTO salaryDTO = new SalaryDTO();
        salaryDTO.setPayDate(YearMonth.from(payDate));
        salaryDTO.setBasic((int) basic);
        salaryDTO.setHra((int) hra);
        salaryDTO.setSpecialAllowance((int) specialAllowance);
        salaryDTO.setPf((int) pf);
        salaryDTO.setProfTax((int) profTax);
        salaryDTO.setGroupMedicalDeduction((int) groupMedicalDeduction);
        salaryDTO.setGroupPolicyAccidentDeduction((int) groupPolicyAccidentDeduction);
        salaryDTO.setTotalEarning(totalSalary);
        salaryDTO.setTotalDeduction((int) totalDeduction);
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
        SalaryDTO salaryDTO = salary == 0 ? new SalaryDTO() : TotalSalaryDetails(salary, date);
        return ResponseEntity.status(HttpStatus.OK).body(salaryDTO);

    }
}
