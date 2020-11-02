package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.EmployeeData;
import com.nineleaps.greytHRClone.model.Swipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SwipesRepository extends JpaRepository<Swipe, Integer> {

    @Query(value = "select * from swipes where emp_id=?1",nativeQuery = true)
    List<Swipe> findByUser(int user);
}
