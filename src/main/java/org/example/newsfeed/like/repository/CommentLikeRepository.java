package org.example.newsfeed.like.repository;

import org.example.newsfeed.like.entity.CommentLike;
import org.example.newsfeed.like.entity.Tablelike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
}
