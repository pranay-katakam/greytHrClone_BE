package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.EmployeeData;
import com.nineleaps.greytHRClone.model.EmployeeLeave;
import com.nineleaps.greytHRClone.model.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeLeaveRepository extends JpaRepository<EmployeeLeave, Integer> {

    @Query("select u from EmployeeLeave u where u.user=?1")
    Iterable<EmployeeLeave> getLeaves(EmployeeData employeeData);

//    @Query("SELECT u from EmployeeLeave u where CURRENT_DATE  BETWEEN fromDate AND toDate")
//    List<EmployeeLeave> getAppliedLeave();


    List<EmployeeLeave> findByUserAndLeaveDateBetweenAndLeavetype(EmployeeData employeeData, LocalDate beginDate, LocalDate lastDate, LeaveStatus approved);
}
