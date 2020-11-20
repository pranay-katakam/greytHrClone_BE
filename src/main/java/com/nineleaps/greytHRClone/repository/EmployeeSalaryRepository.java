package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.EmployeeSalary;
import org.json.simple.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository

public interface EmployeeSalaryRepository extends JpaRepository<EmployeeSalary,Integer > {
   @Query("select u from EmployeeSalary u where u.eid=?1")
    List<EmployeeSalary> getSalaryDetails(int eid);


    List<EmployeeSalary> findByEidOrderBySidDesc(int eid);
}
