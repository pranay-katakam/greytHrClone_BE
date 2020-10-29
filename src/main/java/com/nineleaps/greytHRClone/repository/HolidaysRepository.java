package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.Holidays;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidaysRepository extends CrudRepository<Holidays, Integer> {



}
