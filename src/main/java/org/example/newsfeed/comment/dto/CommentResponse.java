package org.example.newsfeed.comment.dto;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class CommentResponse {

    private final Long id;
    private final Long feedId;
    private final String username;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CommentResponse(Long id, Long feedId, String username, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.feedId = feedId;
        this.username = username;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}


