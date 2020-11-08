package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.EmployeeData;
import com.nineleaps.greytHRClone.model.Swipe;
import org.json.simple.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


public interface SwipesRepository extends JpaRepository<Swipe, Integer> {

//    @Query("select u from Swipe u where u.user=?1")
//    Iterable<Swipe> getSwipes(EmployeeData employeeData);

    Iterable<Swipe> findByUser(EmployeeData employeeData);


    @Query("select u from Swipe u where  DATE(u.createdDate) = CURRENT_DATE ")
    List<Swipe> findByDate();


}
