package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.EmployeeData;
import com.nineleaps.greytHRClone.model.Swipe;
import org.json.simple.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface SwipesRepository extends JpaRepository<Swipe, Integer> {

//    @Query("select u from Swipe u where u.user=?1")
//    Iterable<Swipe> getSwipes(EmployeeData employeeData);

    List<Swipe> findByUser(EmployeeData employeeData);


    @Query("select u from Swipe u where  DATE(u.createdDate) = CURRENT_DATE ")
    List<Swipe> findByDate();

    @Query(value = "select * from swipes where emp_id=3 order by created_date desc limit 4",nativeQuery = true)
    Iterable<Swipe> getRecentSwipes(EmployeeData user);

    List<Swipe> findByUserAndCreatedDateBetween(EmployeeData employeeData, LocalDateTime beginDate, LocalDateTime lastDate);

//    @Query("SELECT e FROM Swipe e WHERE e.createdDate BETWEEN :beginDate AND :endDate ")
//    List<Swipe> limitedSwipes(EmployeeData employeeData, LocalDate beginDate,LocalDate endDate);
}
