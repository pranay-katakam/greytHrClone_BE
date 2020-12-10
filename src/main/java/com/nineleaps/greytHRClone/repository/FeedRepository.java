package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.Feed;
import com.nineleaps.greytHRClone.model.FeedType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface FeedRepository extends JpaRepository<Feed,Integer> {

    Iterable<Feed> findByFeedType(FeedType feedType);
}
