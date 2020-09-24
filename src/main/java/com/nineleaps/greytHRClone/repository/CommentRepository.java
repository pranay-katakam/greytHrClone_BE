package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
}
