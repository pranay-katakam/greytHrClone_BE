package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.Swipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface SwipesRepository extends JpaRepository<Swipe, Integer> {
}
