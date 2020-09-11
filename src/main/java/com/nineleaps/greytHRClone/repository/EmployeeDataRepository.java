package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.EmployeeData;

//import org.json.JSONObject;
import org.json.simple.JSONObject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository


public interface EmployeeDataRepository extends CrudRepository<EmployeeData, Integer> {

    EmployeeData findByName(String name);


    @Query(value = "select emp_id,email,name from employee_data where emp_id=?1",nativeQuery = true)
    JSONObject profile(int id);
}
