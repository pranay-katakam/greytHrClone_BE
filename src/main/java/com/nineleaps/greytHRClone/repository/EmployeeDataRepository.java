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


    @Query("Select count(e) from EmployeeData e where e.email=?1")
    int exist(String email);

    @Query(value = "select emp_id,email,password from employee_data where email=?1", nativeQuery = true)
    JSONObject UserByEmail(String email);

    @Query(value = "select name from employee_data where MONTH(dob)=MONTH(CURDATE()) and DAY(dob)=DAY(CURDATE())", nativeQuery = true)
    List<JSONObject> BirthdayList();


    @Query(value = "select name,created_date,DATEDIFF(NOW(),created_date) as difference from employee_data where MONTH(created_date)=MONTH(CURDATE()) and DAY(created_date)=DAY(CURDATE()) and  created_date <= DATE_SUB(NOW(),INTERVAL 1 YEAR) ", nativeQuery = true)
    List<JSONObject> AnniversaryList();

    @Query("select name from EmployeeData where empId=?1")
    String getManagerName(int mangerId);


    @Query(value = "select emp_id,name from employee_data", nativeQuery = true)
    List<JSONObject> getAllEmployee();

    @Transactional
    @Modifying
    @Query(value = "UPDATE employee_data  SET manager_id=?1 where emp_id =?2", nativeQuery = true)
    void assignManager(int mid, int eid);

    @Transactional
    @Modifying
    @Query(value = "UPDATE employee_data  SET name=?1 where emp_id =?2", nativeQuery = true)
    void updateName(String name, int eid);


    @Transactional
    @Modifying
    @Query("update EmployeeData set imageName=?1 where empId=?2")
    void saveImageById(String ImageName,int id);

    @Query("select empId from EmployeeData")
    List<Integer> findAlluserId();
}
