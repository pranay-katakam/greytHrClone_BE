package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.EmployeeData;
import com.nineleaps.greytHRClone.model.EmployeeDepartment;
import org.json.simple.JSONObject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeDepartmentRepository extends CrudRepository<EmployeeDepartment, Integer> {
    @Query(value = "select department from employee_department;", nativeQuery = true)
    JSONObject getDepartments();
}
