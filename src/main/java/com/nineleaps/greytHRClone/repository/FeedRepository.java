package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface FeedRepository extends CrudRepository<Feed,Integer> {

}
