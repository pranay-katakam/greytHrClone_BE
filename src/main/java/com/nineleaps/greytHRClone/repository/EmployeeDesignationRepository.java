package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.EmployeeDepartment;
import com.nineleaps.greytHRClone.model.EmployeeDesignation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeDesignationRepository extends CrudRepository<EmployeeDesignation,Integer> {
    @Query("select u from EmployeeDesignation u")
    List<EmployeeDesignation> getDesignations();
}
