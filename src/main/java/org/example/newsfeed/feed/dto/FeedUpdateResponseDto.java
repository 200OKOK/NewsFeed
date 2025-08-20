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
    private final LocalDateTime modifiedAt;

    public FeedUpdateResponseDto(Long feedId, String userId, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.feedId = feedId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

}

