package org.example.newsfeed.feed.repository;

import org.example.newsfeed.feed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    List<Feed> findByCreatedAtBetween(LocalDateTime createdAtAfter, LocalDateTime createdAtBefore);
    List<Feed> findAllByUser_IdInOrderByCreatedAtDesc(List<Long> userIds);
}
