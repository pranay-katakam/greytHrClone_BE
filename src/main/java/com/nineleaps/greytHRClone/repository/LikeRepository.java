package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.EmployeeData;
import com.nineleaps.greytHRClone.model.Liked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface LikeRepository extends JpaRepository<Liked, Integer> {
    @Query("select count(feedId) from Liked where user=?1 and feedId=?2 ")
    int existLike(EmployeeData user, int flId);

    @Transactional
    @Modifying
    @Query("Delete from Liked where user=?1 and feedId=?2")
    void deleteLike(EmployeeData user, int flId);
}
