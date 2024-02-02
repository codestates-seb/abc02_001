package com.example.codestates.comment.repository;

import com.example.codestates.band.entity.Band;
import com.example.codestates.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CommentRepository extends JpaRepository <Comment,Long> {
}
