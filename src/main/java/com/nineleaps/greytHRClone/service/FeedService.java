package com.nineleaps.greytHRClone.service;

import com.nineleaps.greytHRClone.dto.EventDTO;
import com.nineleaps.greytHRClone.repository.EmployeeDataRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class FeedService {
    @Autowired
    private EmployeeDataRepository employeeDataRepository;


    public ResponseEntity<List<EventDTO>> events() {

        List<JSONObject> birthdayList= employeeDataRepository.BirthdayList();
        List<JSONObject> anniversaryList=employeeDataRepository.AnniversaryList();

        List<EventDTO> eventDTOS = new ArrayList<>();

        for (JSONObject bDay : birthdayList) {
            EventDTO eventDTO = new EventDTO();
            eventDTO.setName((String) bDay.get("name"));
            eventDTO.setEventType(EventDTO.EventType.BIRTHDAY);
            eventDTO.setDate((Date) bDay.get("dob"));
            eventDTOS.add(eventDTO);
        }

        for (JSONObject anniversary : anniversaryList) {
            EventDTO eventDTO = new EventDTO();
            eventDTO.setName((String) anniversary.get("name"));
            eventDTO.setEventType(EventDTO.EventType.ANNIVERSARY);
            eventDTO.setDate((Date)anniversary.get("created_date"));
            BigInteger diff=(BigInteger)anniversary.get("difference");
            int difference=diff.intValue()/365;
            eventDTO.setNumberOfYears(difference);
            eventDTOS.add(eventDTO);
        }


//        eventDTOS.sort(Comparator.comparing(EventDTO::getDate));
//          eventDTOS.stream().sorted(comparing(EventDTO::getDate).reversed());
//        eventDTOS.sort(comparing(EventDTO::getDate).reversed());/////////

        return ResponseEntity.status(HttpStatus.OK).body(eventDTOS);
    }

}
