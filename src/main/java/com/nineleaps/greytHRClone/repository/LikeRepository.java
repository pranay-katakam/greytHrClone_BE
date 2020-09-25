package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.Liked;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Liked,Integer> {
}
