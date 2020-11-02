package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.EmployeeData;
import com.nineleaps.greytHRClone.model.EmployeeLeave;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeLeaveRepository extends CrudRepository<EmployeeLeave, Integer> {

    @Query("select u from EmployeeLeave u where u.user=?1")
    Iterable<EmployeeLeave> getLeaves(EmployeeData employeeData);

}
