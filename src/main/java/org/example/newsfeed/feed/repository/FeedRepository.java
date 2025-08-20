package org.example.newsfeed.feed.repository;

import org.example.newsfeed.feed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long> {
}
