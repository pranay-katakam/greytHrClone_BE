package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.EmployeeData;

//import org.json.JSONObject;
import org.json.simple.JSONObject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository


public interface EmployeeDataRepository extends CrudRepository<EmployeeData, Integer> {

//    EmployeeData findByEmail(String email);



    @Query(value = " SELECT * FROM (select emp_id,name,employee_designation,employee_department,location,manager_id from employee_data where emp_id=?1 ) a CROSS JOIN (select name as manager_name from employee_data where emp_id=(select manager_id from employee_data where emp_id=?1))  b", nativeQuery = true)

    JSONObject profile(int id);

    @Query(value = "Select count(name) from employee_data where email=?1", nativeQuery = true)
    int exist(String email);

    @Query(value = "select email,password from employee_data where email=?1", nativeQuery = true)
    JSONObject UserByEmail(String email);
}
