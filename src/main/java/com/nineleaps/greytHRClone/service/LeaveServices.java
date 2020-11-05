package com.nineleaps.greytHRClone.service;

import com.nineleaps.greytHRClone.dto.*;

import com.nineleaps.greytHRClone.model.*;
import com.nineleaps.greytHRClone.repository.EmployeeDataRepository;
import com.nineleaps.greytHRClone.repository.EmployeeLeaveRepository;
import com.nineleaps.greytHRClone.repository.HolidaysRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import java.util.List;

@Service
public class LeaveServices {

    private HolidaysRepository holidaysRepository;
    private EmployeeLeaveRepository employeeLeaveRepository;
    private EmployeeDataRepository employeeDataRepository;

    public LeaveServices(HolidaysRepository holidaysRepository, EmployeeLeaveRepository employeeLeaveRepository, EmployeeDataRepository employeeDataRepository) {
        this.holidaysRepository = holidaysRepository;
        this.employeeLeaveRepository = employeeLeaveRepository;
        this.employeeDataRepository = employeeDataRepository;
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

    public ResponseEntity<String> applyLeave(EmployeeLeaveRequestDTO employeeLeaveRequestDTO) {
        EmployeeData employeeData = new EmployeeData();
        employeeData.setEmpId(employeeLeaveRequestDTO.getUserId());

        EmployeeLeave employeeLeaves = new EmployeeLeave();
        employeeLeaves.setUser(employeeData);
        employeeLeaves.setLeavetype(employeeLeaveRequestDTO.getLeavetype());
        employeeLeaves.setReason(employeeLeaveRequestDTO.getReason());
        employeeLeaves.setFromDate(employeeLeaveRequestDTO.getFromDate());
        employeeLeaves.setToDate(employeeLeaveRequestDTO.getToDate());
        employeeLeaves.setLeaveStatus(LeaveStatus.PENDING);

        employeeLeaveRepository.save(employeeLeaves);
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
            employeeLeaveDTO.setFromDate(leave.getFromDate());
            employeeLeaveDTO.setToDate(leave.getToDate());
            employeeLeaveDTO.setReason(leave.getReason());

            employeeLeaveDTOS.add(employeeLeaveDTO);

        }
        return ResponseEntity.status(HttpStatus.OK).body(employeeLeaveDTOS);
    }
}
