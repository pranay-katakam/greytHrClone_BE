package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.EmployeeSalary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeSalaryRepository extends CrudRepository<EmployeeSalary,Integer > {
}
