package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.EmployeeSalary;
import org.json.simple.JSONObject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface EmployeeSalaryRepository extends CrudRepository<EmployeeSalary,Integer > {
   @Query(value = "select total_salary from employee_salary where eid=?1",nativeQuery = true)
    List<JSONObject> getSalaryDetails(int eid);

}
