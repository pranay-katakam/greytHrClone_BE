package com.nineleaps.greytHRClone.service;

import com.nineleaps.greytHRClone.model.Holidays;
import com.nineleaps.greytHRClone.repository.HolidaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveServices {
    @Autowired
    private HolidaysRepository holidaysRepository;

    public ResponseEntity<String> addHolidays(Iterable<Holidays> holidays) {
        holidaysRepository.saveAll(holidays);
        return ResponseEntity.status(HttpStatus.CREATED).body("holiday added successfully");
    }

    public ResponseEntity<Iterable<Holidays>> getHolidays() {
        return ResponseEntity.status(HttpStatus.OK).body(holidaysRepository.findAll());
    }
}
