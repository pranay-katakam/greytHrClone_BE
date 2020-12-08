package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Integer> {


}
