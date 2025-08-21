package org.example.newsfeed.feed.repository;

import org.example.newsfeed.feed.dto.FeedByDateResponseDto;
import org.example.newsfeed.feed.entity.Feed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    List<Feed> findByCreatedAtBetween(LocalDateTime createdAtAfter, LocalDateTime createdAtBefore);
}
