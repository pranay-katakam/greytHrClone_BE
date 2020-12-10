package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.CompanyLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CompanyLocationRepository  extends JpaRepository<CompanyLocation, Integer> {
}
