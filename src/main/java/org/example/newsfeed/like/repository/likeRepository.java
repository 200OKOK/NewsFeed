package org.example.newsfeed.like.repository;

import org.example.newsfeed.like.entity.like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface likeRepository extends JpaRepository<like, Long> {
}
