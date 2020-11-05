package com.nineleaps.greytHRClone.repository;


import com.nineleaps.greytHRClone.model.EmployeeDepartment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeDepartmentRepository extends CrudRepository<EmployeeDepartment,Integer> {
    @Query("select u from EmployeeDepartment u")
    List<EmployeeDepartment> getDepartments();
}
