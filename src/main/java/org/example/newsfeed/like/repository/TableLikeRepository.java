package org.example.newsfeed.like.repository;

import org.example.newsfeed.like.entity.Feedlike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableLikeRepository extends JpaRepository<Feedlike, Long> {
    boolean existsByUser_IdAndFeed_FeedId(Long userId, Long feedId);

    void deleteByUser_IdAndFeed_FeedId(Long userId, Long feedId);
}
