package com.nineleaps.greytHRClone.service;

import com.nineleaps.greytHRClone.dto.*;

import com.nineleaps.greytHRClone.exception.BadRequestException;
import com.nineleaps.greytHRClone.model.*;
import com.nineleaps.greytHRClone.repository.EmployeeDataRepository;
import com.nineleaps.greytHRClone.repository.EmployeeLeaveRepository;
import com.nineleaps.greytHRClone.repository.HolidaysRepository;
import com.nineleaps.greytHRClone.repository.LeaveBalanceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import java.util.List;

import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

@Service
public class LeaveServices {

    private HolidaysRepository holidaysRepository;
    private EmployeeLeaveRepository employeeLeaveRepository;
    private EmployeeDataRepository employeeDataRepository;
    private LeaveBalanceRepository leaveBalanceRepository;

    public LeaveServices(HolidaysRepository holidaysRepository, EmployeeLeaveRepository employeeLeaveRepository, EmployeeDataRepository employeeDataRepository, LeaveBalanceRepository leaveBalanceRepository) {
        this.holidaysRepository = holidaysRepository;
        this.employeeLeaveRepository = employeeLeaveRepository;
        this.employeeDataRepository = employeeDataRepository;
        this.leaveBalanceRepository = leaveBalanceRepository;
    }

    public ResponseEntity<String> addHolidays(List<HolidayDTO> holidayDTOS) {
        ModelMapper modelMapper = new ModelMapper();
        Iterable<Holidays> Holidays = Arrays.asList( modelMapper.map(holidayDTOS, Holidays[].class));
        holidaysRepository.saveAll(Holidays);
        return ResponseEntity.status(HttpStatus.CREATED).body("holiday added successfully");
    }


    public ResponseEntity<List<Holidays>> getHolidays() {
        return ResponseEntity.status(HttpStatus.OK).body(holidaysRepository.findAll());
    }

    public ResponseEntity<List<Holidays>> getUpcomingHolidays() {
        return ResponseEntity.status(HttpStatus.OK).body(holidaysRepository.findByHolidayDateBetween(LocalDate.now() ,LocalDate.now().with(lastDayOfYear())));
    }


    public ResponseEntity<String> applyLeave(EmployeeLeaveRequestDTO employeeLeaveRequestDTO) {
        EmployeeData employeeData = new EmployeeData();
        employeeData.setEmpId(employeeLeaveRequestDTO.getUserId());
        LocalDateTime toDate=employeeLeaveRequestDTO.getToDate().plusDays(1);
        List<EmployeeLeave> employeeLeaves=new ArrayList<>();
        for (LocalDateTime date = employeeLeaveRequestDTO.getFromDate(); date.isBefore(toDate); date = date.plusDays(1)){
            EmployeeLeave employeeLeave = new EmployeeLeave();
            employeeLeave.setUser(employeeData);
            employeeLeave.setLeavetype(employeeLeaveRequestDTO.getLeavetype());
            employeeLeave.setReason(employeeLeaveRequestDTO.getReason());
            employeeLeave.setLeaveDate(date);
            employeeLeave.setLeaveStatus(LeaveStatus.PENDING);
            employeeLeaves.add(employeeLeave);
        }
        employeeLeaveRepository.saveAll(employeeLeaves);
        return ResponseEntity.status(HttpStatus.CREATED).body("Leave applied Successfully");
    }

    public ResponseEntity<List<EmployeeLeaveDTO>> getLeaves(int id) {
        EmployeeData employeeData = new EmployeeData();
        employeeData.setEmpId(id);

        Iterable<EmployeeLeave> leaves = employeeLeaveRepository.getLeaves(employeeData);

        List<EmployeeLeaveDTO> employeeLeaveDTOS = new ArrayList<>();


        for (EmployeeLeave leave : leaves) {
            String managerName = employeeDataRepository.getManagerName(leave.getUser().getManagerId());
            EmployeeLeaveDTO employeeLeaveDTO = new EmployeeLeaveDTO();
            employeeLeaveDTO.setLeaveId(leave.getLeaveId());
            employeeLeaveDTO.setEmpId(leave.getUser().getEmpId());
            employeeLeaveDTO.setName(leave.getUser().getName());
            employeeLeaveDTO.setManagerId(leave.getUser().getManagerId());
            employeeLeaveDTO.setManagerName(managerName);
            employeeLeaveDTO.setLeavetype(leave.getLeavetype());
            employeeLeaveDTO.setLeaveStatus(leave.getLeaveStatus());
            employeeLeaveDTO.setToDate(leave.getLeaveDate());
            employeeLeaveDTO.setReason(leave.getReason());

            employeeLeaveDTOS.add(employeeLeaveDTO);

        }
        return ResponseEntity.status(HttpStatus.OK).body(employeeLeaveDTOS);
    }


    public void addEarnedLeaveMonthly() {
        List<Integer> empIDs = employeeDataRepository.findAlluserId();
        List<LeaveBalance> leaveBalances = leaveBalanceRepository.findAll();
        EmployeeData employeeData = new EmployeeData();
        int earnedLeave = 0;
        for (Integer empID : empIDs){
            LeaveBalance leaveBalance = leaveBalances.stream()
                    .filter(t -> t.getUser().getEmpId()==empID)
                    .findAny()
                    .orElse(new LeaveBalance());
            earnedLeave = leaveBalance.getEarnedLeave() + 1;
            leaveBalance.setEarnedLeave(earnedLeave);
            employeeData.setEmpId(empID);
            leaveBalance.setUser(employeeData);
            leaveBalanceRepository.save(leaveBalance);
        }
    }


}
