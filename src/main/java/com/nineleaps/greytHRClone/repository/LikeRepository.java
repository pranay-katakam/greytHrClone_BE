package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.Liked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface LikeRepository extends JpaRepository<Liked, Integer> {
    @Query("select count(eid) from Liked where eid=?1 and flId=?2 ")
    int existLike(int eid, int flId);

    @Transactional
    @Modifying
    @Query("Delete from Liked where eid=?1 and flId=?2")
    void deleteLike(int eid, int flId);
}
