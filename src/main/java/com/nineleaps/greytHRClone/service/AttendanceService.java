package com.nineleaps.greytHRClone.service;

import com.nineleaps.greytHRClone.dto.DoorAddressDTO;
import com.nineleaps.greytHRClone.dto.SwipeDTO;
import com.nineleaps.greytHRClone.dto.SwipesDTO;
import com.nineleaps.greytHRClone.model.DoorAddress;
import com.nineleaps.greytHRClone.model.EmployeeData;
import com.nineleaps.greytHRClone.model.Swipe;
import com.nineleaps.greytHRClone.repository.DoorAddressRepository;
import com.nineleaps.greytHRClone.repository.EmployeeDataRepository;
import com.nineleaps.greytHRClone.repository.SwipesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AttendanceService {


    private DoorAddressRepository doorAddressRepository;
    private SwipesRepository swipesRepository;
    private EmployeeDataRepository employeeDataRepository;


    public AttendanceService(DoorAddressRepository doorAddressRepository, SwipesRepository swipesRepository,EmployeeDataRepository employeeDataRepository) {
        this.doorAddressRepository = doorAddressRepository;
        this.swipesRepository = swipesRepository;
        this.employeeDataRepository=employeeDataRepository;

    }

    public ResponseEntity<String> addDoorAddress(List<DoorAddressDTO> doorAddressDTOS) {
        ModelMapper modelMapper = new ModelMapper();
        Iterable<DoorAddress> DoorAddresses = Arrays.asList( modelMapper.map(doorAddressDTOS, DoorAddress[].class));
        doorAddressRepository.saveAll(DoorAddresses);
        return ResponseEntity.status(HttpStatus.CREATED).body("added door address successfully!");
    }

    public ResponseEntity<List<DoorAddress>> getDoorAddress() {
//        Iterable<DoorAddress> doorAddressesIterable=doorAddressRepository.findAll();
//        List<DoorAddress> doorAddresses=StreamSupport.stream(doorAddressesIterable.spliterator(), false)
//                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(doorAddressRepository.getDoorAddress());
    }

    public ResponseEntity<String> addSwipe(SwipeDTO swipeDTO) {
        EmployeeData employeeData=new EmployeeData();
        employeeData.setEmpId(swipeDTO.getUserId());

        DoorAddress doorAddress=new DoorAddress();
        doorAddress.setAddressId(swipeDTO.getDoorAddressId());

        Swipe swipe=new Swipe();
        swipe.setUser(employeeData);
        swipe.setDoorAddress(doorAddress);
        swipesRepository.save(swipe);
        return ResponseEntity.status(HttpStatus.CREATED).body("recent swipe recorded successfully");

    }


    public ResponseEntity<List<SwipesDTO>> getSwipes(int id) {

        EmployeeData employeeData = new EmployeeData();
        employeeData.setEmpId(id);
        Iterable<Swipe> swipes = swipesRepository.getSwipes(employeeData);

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
        ;
        return ResponseEntity.status(HttpStatus.OK).body(swipesDTOs);

    }


   public void markAttendence() {

        List<Swipe> swipeUsers= swipesRepository.findByDate();

       List<EmployeeData> users=swipeUsers.stream()
                .map(Swipe::getUser).distinct()
                .collect(Collectors.toList());


       for(EmployeeData user:users){

       }
       System.out.println("user " +users);
        //for loop to collect first swipe and last swipe of the day


       List<Integer> allUserIds=employeeDataRepository.findAlluserId();

        List<Integer> absenties=new ArrayList<>();

       for (Integer item : allUserIds) {
           if (!swipeUsers.contains(item)) {
               absenties.add(item);
           }
       }

       System.out.println(absenties);

       //amoung these absent did anyone had already applied for leave
       //change the attendance category from absent to leave



       System.out.println("swipeUsers "+swipeUsers);
       System.out.println("allUserIds "+allUserIds);
        System.out.println(" in mark attendence");
    }

}

