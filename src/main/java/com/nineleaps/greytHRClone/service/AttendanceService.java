package com.nineleaps.greytHRClone.service;

import com.nineleaps.greytHRClone.dto.*;
import com.nineleaps.greytHRClone.helper.MailContentBuilder;
import com.nineleaps.greytHRClone.model.*;
import com.nineleaps.greytHRClone.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AttendanceService {

    private DoorAddressRepository doorAddressRepository;
    private SwipesRepository swipesRepository;
    private EmployeeDataRepository employeeDataRepository;
    private MailContentBuilder mailContentBuilder;
    private EmployeeLeaveRepository employeeLeaveRepository;
    private EmployeeAttendanceRepository employeeAttendanceRepository;

    @Autowired
    public AttendanceService(DoorAddressRepository doorAddressRepository, SwipesRepository swipesRepository, EmployeeDataRepository employeeDataRepository, MailContentBuilder mailContentBuilder, EmployeeLeaveRepository employeeLeaveRepository, EmployeeAttendanceRepository employeeAttendanceRepository) {
        this.doorAddressRepository = doorAddressRepository;
        this.swipesRepository = swipesRepository;
        this.employeeDataRepository = employeeDataRepository;
        this.mailContentBuilder = mailContentBuilder;
        this.employeeLeaveRepository = employeeLeaveRepository;
        this.employeeAttendanceRepository = employeeAttendanceRepository;
    }




    public ResponseEntity<ApiResponseDTO> addDoorAddress(List<DoorAddressDTO> doorAddressDTOS) {
        ModelMapper modelMapper = new ModelMapper();
        Iterable<DoorAddress> DoorAddresses = Arrays.asList( modelMapper.map(doorAddressDTOS, DoorAddress[].class));
        doorAddressRepository.saveAll(DoorAddresses);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO("added door address successfully!"));
    }

    public ResponseEntity<List<DoorAddress>> getDoorAddress() {
        return ResponseEntity.status(HttpStatus.OK).body(doorAddressRepository.findAll());
    }

    public ResponseEntity<ApiResponseDTO> addSwipe(SwipeDTO swipeDTO) {
        EmployeeData employeeData=new EmployeeData();//converting int userId to to type EmployeeData
        employeeData.setEmpId(swipeDTO.getUserId());
        DoorAddress doorAddress=new DoorAddress();//converting int doorAddressId to type DoorAddress
        doorAddress.setAddressId(swipeDTO.getDoorAddressId());

        Swipe swipe=new Swipe();
        swipe.setUser(employeeData);
        swipe.setDoorAddress(doorAddress);
        swipesRepository.save(swipe);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO("recent swipe recorded successfully"));
    }

    public ResponseEntity<List<SwipesDTO>> getSwipes(int id) {
        EmployeeData employeeData = new EmployeeData();//converting int id to type EmployeeData
        employeeData.setEmpId(id);
//        Iterable<Swipe> swipes = swipesRepository.getSwipes(employeeData);
        Iterable<Swipe> swipes = swipesRepository.findByUser(employeeData);
        List<SwipesDTO> swipesDTOs = new ArrayList<>();
        for (Swipe swipe : swipes) {
            SwipesDTO swipesDTO = new SwipesDTO();
            swipesDTO.setSwipeId(swipe.getSwipeId());
            swipesDTO.setEmployeeId(swipe.getUser().getEmpId());
            swipesDTO.setCreatedDate(swipe.getCreatedDate());
            swipesDTO.setEmployeeName(swipe.getUser().getName());
            swipesDTO.setDoorAddress(swipe.getDoorAddress().getDoorName());
            swipesDTOs.add(swipesDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(swipesDTOs);
    }

   public void markAttendence() {
       List<EmployeeAttendance> employeeAttendances = new ArrayList<>();

       List<Swipe> swipeUsers = swipesRepository.findByDate();//10
       List<TotalTimeDTO> totalTimeDTOS = getTotalTime(swipeUsers);//9

       List<Integer> allUserIds = employeeDataRepository.findAlluserId();//20

       //check for 4<8 of working hours::half day
       //send alert mail to admin and user about time deduction
       for (TotalTimeDTO totalTimeDTO : totalTimeDTOS) {
           EmployeeAttendance employeeAttendance = new EmployeeAttendance();
           if (totalTimeDTO.getTotalTime() < 8 && totalTimeDTO.getTotalTime() > 4) {
               employeeAttendance.setAttendanceCategory(AttendanceCategory.HALF_DAY);
               EmployeeData employeeData = new EmployeeData();
               employeeData.setEmpId(totalTimeDTO.getEmployeeId());
               employeeAttendance.setUser(employeeData);
               employeeAttendances.add(employeeAttendance);
               mailContentBuilder.sendDeductionMail(totalTimeDTO);
               allUserIds.remove(totalTimeDTO.getEmployeeId());

           } else {
               allUserIds.remove(totalTimeDTO.getEmployeeId());

           }
       }

       //amoung these absent did anyone had already applied for leave
       //change the attendance category from absent to leave
       List<EmployeeLeave> employeeLeaves = employeeLeaveRepository.getAppliedLeave();
       //allUserIds are the Ids after filtering presenties
       for (Integer absenteeId : allUserIds) {
           EmployeeAttendance employeeAttendance = new EmployeeAttendance();
           EmployeeData employeeData=new EmployeeData();
           employeeData.setEmpId(absenteeId);
           //TODO check if the leave was approved or not
           if (employeeLeaves.contains(absenteeId)) {
               employeeAttendance.setUser(employeeData);
               employeeAttendance.setAttendanceCategory(AttendanceCategory.LEAVE);
               employeeAttendances.add(employeeAttendance);
           }else{
               employeeAttendance.setUser(employeeData);
               employeeAttendance.setAttendanceCategory(AttendanceCategory.ABSENT);
               employeeAttendances.add(employeeAttendance);
           }
       }

       employeeAttendanceRepository.saveAll(employeeAttendances);
   }

    public List<TotalTimeDTO> getTotalTime(List<Swipe> swipes){
        List<EmployeeData> users=swipes.stream()
                .map(Swipe::getUser).distinct()
                .collect(Collectors.toList());

        List<TotalTimeDTO> totalTimeDTOS=new ArrayList<>();


        for(EmployeeData user:users){
            List<LocalDateTime> AllSwipesPerUser=swipes.stream()
                    .filter(s ->s.getUser()==user)
                    .map(Swipe::getCreatedDate)
                    .collect(Collectors.toList());

            LocalDateTime firstSwipe=AllSwipesPerUser.stream()
                    .findFirst().get();

            long count = AllSwipesPerUser.stream().count();
            Stream<LocalDateTime> stream = AllSwipesPerUser.stream();
            LocalDateTime lastSwipe=stream.skip(count - 1).findFirst().get();

            Duration duration = Duration.between(lastSwipe, firstSwipe);
            long diff = Math.abs(duration.toHours());
            TotalTimeDTO totalTimeDTO=new TotalTimeDTO(user.getEmpId(),user.getName(),firstSwipe,lastSwipe,diff);
            totalTimeDTOS.add(totalTimeDTO);
                    }
        return totalTimeDTOS;
    }





}

