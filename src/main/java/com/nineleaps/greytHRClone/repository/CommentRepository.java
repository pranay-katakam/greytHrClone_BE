package com.nineleaps.greytHRClone.repository;

import com.nineleaps.greytHRClone.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment,Integer> {
}
