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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.http.HttpStatus.OK;

@Service
public class AttendanceService {

    private DoorAddressRepository doorAddressRepository;
    private SwipesRepository swipesRepository;
    private EmployeeDataRepository employeeDataRepository;
    private EmployeeLeaveRepository employeeLeaveRepository;
    private HolidaysRepository holidaysRepository;
    private RegularizationRepository regularizationRepository;

    @Autowired
    public AttendanceService(DoorAddressRepository doorAddressRepository, SwipesRepository swipesRepository, EmployeeDataRepository employeeDataRepository, EmployeeLeaveRepository employeeLeaveRepository,HolidaysRepository holidaysRepository,RegularizationRepository regularizationRepository) {
        this.doorAddressRepository = doorAddressRepository;
        this.swipesRepository = swipesRepository;
        this.employeeDataRepository = employeeDataRepository;
        this.employeeLeaveRepository = employeeLeaveRepository;
        this.holidaysRepository=holidaysRepository;
        this.regularizationRepository=regularizationRepository;
    }

    @Autowired
    private MailContentBuilder mailContentBuilder;



    public ResponseEntity<ApiResponseDTO> addDoorAddress(List<DoorAddressDTO> doorAddressDTOS) {
        ModelMapper modelMapper = new ModelMapper();
        Iterable<DoorAddress> DoorAddresses = Arrays.asList( modelMapper.map(doorAddressDTOS, DoorAddress[].class));
        doorAddressRepository.saveAll(DoorAddresses);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO("added door address successfully!"));
    }

    public ResponseEntity<List<DoorAddress>> getDoorAddress() {
        return ResponseEntity.status(OK).body(doorAddressRepository.findAll());
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
        List<Swipe> swipes = swipesRepository.findByUser(employeeData);
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
        return ResponseEntity.status(OK).body(swipesDTOs);
    }

   public void markAttendence() {

       List<Swipe> swipeUsers = swipesRepository.findByDate();//15
       List<TotalTimeDTO> totalTimeDTOS = getTotalTime(swipeUsers);//15

       //check for 4<8 of working hours::half day
       //send alert mail to admin and user about time deduction
       for (TotalTimeDTO totalTimeDTO : totalTimeDTOS) {
           if (totalTimeDTO.getTotalTime() < 8 && totalTimeDTO.getTotalTime() > 4) {
               mailContentBuilder.sendDeductionMail(totalTimeDTO);
           }

       }
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
            TotalTimeDTO totalTimeDTO=new TotalTimeDTO(user.getEmpId(),diff);
            totalTimeDTOS.add(totalTimeDTO);
                    }
        return totalTimeDTOS;
    }


    public ResponseEntity<AttendanceSummaryDTO> getAttendanceSummary(int id, Optional<LocalDate> startDate, Optional<LocalDate> endDate) {
        LocalDate beginDate= startDate.orElse(startDate.get().withDayOfMonth(1));
        LocalDate lastDate=endDate.orElse(LocalDate.now());
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        DateFormat hourFormat = new SimpleDateFormat("hh:mm a");
        EmployeeData employeeData=new EmployeeData();
        employeeData.setEmpId(id);
        List<Swipe> swipes = swipesRepository.findByUserAndCreatedDateBetween(employeeData,beginDate,lastDate);
        List<EmployeeLeave> employeeLeaves =employeeLeaveRepository.findByUserAndLeaveDateBetweenAndLeavetype(employeeData,beginDate,lastDate,LeaveStatus.APPROVED);
        List<Holidays> holidays=holidaysRepository.findByHolidayDateBetween(beginDate,lastDate);
        List<RegularizationData> regularizationData=regularizationRepository.findByUserAndDateBetweenAndStatus(employeeData,beginDate,lastDate,LeaveStatus.APPROVED);
        List<AttendanceDetailsDTO> attendanceDetailsDTOS=new ArrayList<>();
        int presentDays=0;
        int absent=0;
        int onLeave=0;
        int holidaysS=0;
        int lateInDays=0;
        int earlyOutDays=0;
        for(LocalDate date =beginDate;date.isBefore(lastDate);date = date.plusDays(1)){
            LocalDate loopDate = date;
            RegularizationData regularization=regularizationData.stream()
                    .filter(r->r.getDate().equals(loopDate))
                    .findFirst()
                    .orElse(null);
            AttendanceDetailsDTO attendanceDetailsDTO=new AttendanceDetailsDTO();
            attendanceDetailsDTO.setDate(dateFormat.format(loopDate));

            boolean holiday= holidays.stream().anyMatch(e->e.getHolidayDate().equals(loopDate));
            DayOfWeek day = loopDate.getDayOfWeek();//Sunday,Monday....

           if (holiday) {
               attendanceDetailsDTO.setStatus(AttendanceCategory.HOLIDAY);
               holidaysS=holidaysS+1;
           }
           else if(day.equals(DayOfWeek.SATURDAY)||day.equals(DayOfWeek.SUNDAY)) {
               attendanceDetailsDTO.setStatus(AttendanceCategory.REST_DAY);
           }
           else if(regularization!=null ) {
               Duration duration = Duration.between(regularization.getLastOut(), regularization.getFirstIn());
               long diff = Math.abs(duration.toHours());
               attendanceDetailsDTO.setStatus(AttendanceCategory.REGULARIZED);
               attendanceDetailsDTO.setFirstIn(hourFormat.format(regularization.getFirstIn()));
               attendanceDetailsDTO.setLastOut(hourFormat.format(regularization.getLastOut()));
               attendanceDetailsDTO.setTotalWorkHours(hourFormat.format(diff));
               attendanceDetailsDTO.setRegularizedBy(regularization.getRegularizedBy());
               attendanceDetailsDTO.setRegularizedOn(regularization.getRegularizedOn());
               attendanceDetailsDTO.setRemark(regularization.getRemarks());
           }
           else {
               List<LocalDateTime> AllSwipesPerDay = swipes.stream()
                        .filter(s -> s.getCreatedDate().equals(loopDate))
                        .map(Swipe::getCreatedDate)
                        .collect(Collectors.toList());
               attendanceDetailsDTO.setSwipes(AllSwipesPerDay);
               LocalDateTime firstSwipe = AllSwipesPerDay.stream()
                        .findFirst()
                        .orElse(null);//case for Absent,leave

               EmployeeLeave employeeLeave = employeeLeaves.stream()
                        .filter(s -> s.getLeaveDate().equals(loopDate))
                        .findFirst()
                        .orElse(null);// he has not applied for leave
               if (firstSwipe == null && employeeLeave == null) {
                   attendanceDetailsDTO.setStatus(AttendanceCategory.ABSENT);
                   absent=absent+1;
                }
               else if(employeeLeave!=null){
                   attendanceDetailsDTO.setStatus(employeeLeave.getLeavetype());
                   onLeave=onLeave+1;
               }
               else {
                    long count = AllSwipesPerDay.stream().count();
                    Stream<LocalDateTime> stream = AllSwipesPerDay.stream();
                    LocalDateTime lastSwipe = stream.skip(count - 1)
                            .findFirst()
                            .get();

                    Duration duration = Duration.between(lastSwipe, firstSwipe);
                    long diff = Math.abs(duration.toHours());
                    int balance=0;
                    if (diff < 9 && diff > 4){
                        balance= 9-Math.toIntExact(diff);
                        attendanceDetailsDTO.setStatus(AttendanceCategory.HALF_DAY);
                        attendanceDetailsDTO.setEarlyOutHours(hourFormat.format(balance));
                        earlyOutDays=earlyOutDays+1;
                    }


                    attendanceDetailsDTO.setFirstIn(hourFormat.format(firstSwipe));
                    attendanceDetailsDTO.setLastOut(hourFormat.format(lastSwipe));
                    attendanceDetailsDTO.setTotalWorkHours(hourFormat.format(diff));
                    if(diff>9||diff==9) {
                        attendanceDetailsDTO.setStatus(AttendanceCategory.PRESENT);
                        presentDays=presentDays+1;
                        balance = Math.toIntExact(diff) - 9;
                        attendanceDetailsDTO.setExcessHours(hourFormat.format(balance));
                    }
                    if(firstSwipe.isAfter(loopDate.atTime(10, 15))){
                        attendanceDetailsDTO.setLateInHours(hourFormat.format(firstSwipe));
                        lateInDays=lateInDays+1;
                    }
               }
           }
            attendanceDetailsDTOS.add(attendanceDetailsDTO);
        }
        AttendanceSummaryDTO attendanceSummaryDTO=new AttendanceSummaryDTO();
        attendanceSummaryDTO.setPresentDays(presentDays);
        attendanceSummaryDTO.setAbsent(absent);
        attendanceSummaryDTO.setOnLeave(onLeave);
        attendanceSummaryDTO.setHolidays(holidaysS);
        attendanceSummaryDTO.setLateInDays(lateInDays);
        attendanceSummaryDTO.setEarlyOutDays(earlyOutDays);
        attendanceSummaryDTO.setAttendanceDetails(attendanceDetailsDTOS);
        return ResponseEntity.status(OK).body(attendanceSummaryDTO);
    }
}

