package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.EmployeeData;
import com.nineleaps.greytHRClone.model.Swipe;
import org.json.simple.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SwipesRepository extends JpaRepository<Swipe, Integer> {
    @Query("select * from Swipe where empId=?1")
    Iterable<Swipe> getSwipes(EmployeeData employeeData);
}
