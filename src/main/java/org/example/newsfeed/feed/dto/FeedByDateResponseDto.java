package org.example.newsfeed.feed.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class FeedByDateResponseDto {

    private final Long feedId;
    private final String userId;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static FeedByDateResponseDto of(Long feedId, String userId, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new FeedByDateResponseDto(feedId, userId, title, content, createdAt, updatedAt);
    }
}
