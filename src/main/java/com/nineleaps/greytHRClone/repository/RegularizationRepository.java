package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.EmployeeData;
import com.nineleaps.greytHRClone.model.LeaveStatus;
import com.nineleaps.greytHRClone.model.RegularizationData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RegularizationRepository extends JpaRepository<RegularizationData,Integer> {
    List<RegularizationData> findByUserAndDateBetween(EmployeeData employeeData, LocalDate beginDate, LocalDate lastDate);
}
