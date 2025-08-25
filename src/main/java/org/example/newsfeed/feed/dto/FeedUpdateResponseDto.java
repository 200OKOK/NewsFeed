package org.example.newsfeed.feed.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FeedUpdateResponseDto {
    private final Long feedId;
    private final String userId;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String message;

    public FeedUpdateResponseDto(Long feedId, String userId, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt, String message) {
        this.feedId = feedId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.message = message;
    }
}

