package org.example.newsfeed.feed.dto;

import lombok.Getter;
import org.example.newsfeed.feed.entity.Feed;

import java.time.LocalDateTime;

@Getter
public class FeedPageResponseDto {
    private final Long feedId;
    private final String userId;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;


    public FeedPageResponseDto(Long feedId, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt, String userName) {
        this.feedId = feedId;
        this.userId = userName;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}