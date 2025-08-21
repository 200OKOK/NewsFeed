package org.example.newsfeed.feed.repository;

import org.example.newsfeed.feed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    List<Feed> findAllByUser_UserIdInOrderByCreatedAtDesc(List<Long> userIds);
}
