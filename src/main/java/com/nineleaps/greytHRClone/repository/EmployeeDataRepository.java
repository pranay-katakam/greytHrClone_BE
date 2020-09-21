package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.EmployeeData;

import org.json.simple.JSONObject;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface EmployeeDataRepository extends CrudRepository<EmployeeData, Integer> {

    @Query(value = " select name,designation,department,location,manager_id from employee_data where emp_id=?1 ", nativeQuery = true)

    JSONObject profile(int id);

    @Query(value = "Select count(name) from employee_data where email=?1", nativeQuery = true)
    int exist(String email);

    @Query(value = "select email,password from employee_data where email=?1", nativeQuery = true)
    JSONObject UserByEmail(String email);

    @Query(value = "select name,dob from employee_data where MONTH(dob)<=MONTH(CURDATE()) and DAY(dob)<=DAY(CURDATE()) order by MONTH(dob) desc, DAY(dob) desc",nativeQuery = true)
    List<JSONObject> BirthdayList();

    @Query(value = "select name,created_date from employee_data where  created_date <= DATE_SUB(NOW(),INTERVAL 1 YEAR) order by DATE(created_date) desc;",nativeQuery = true)

    List<JSONObject> AnniversaryList();

    @Query("select name from EmployeeData where empId=?1")
    String getManagerName(int mangerId);

    @Query(value="select emp_id,name from employee_data",nativeQuery = true)
    List<JSONObject> getAllEmployee();

    @Transactional
    @Modifying
    @Query(value = "update employee_data set name=?1 where emp_id=?2", nativeQuery = true)
    void updateName(String name, int eid);
}
