package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.DoorAddress;
import com.nineleaps.greytHRClone.model.Holidays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface HolidaysRepository extends JpaRepository<Holidays, Integer> {

    @Query("select holidayDate from Holidays")
    List<LocalDate> findAllByHolidayDate();

    List<Holidays> findByHolidayDateBetween(LocalDate beginDate, LocalDate lastDate);
//    List<Holidays> getHolidays();
}
