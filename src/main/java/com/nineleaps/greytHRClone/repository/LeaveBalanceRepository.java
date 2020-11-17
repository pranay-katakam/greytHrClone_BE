package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Integer> {

}
