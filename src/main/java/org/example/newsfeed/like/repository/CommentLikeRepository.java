package org.example.newsfeed.like.repository;

import org.example.newsfeed.like.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByUser_IdAndComment_Id(Long userId, Long commentId);
    void deleteByUser_IdAndComment_Id(Long userId, Long commentId);
}
