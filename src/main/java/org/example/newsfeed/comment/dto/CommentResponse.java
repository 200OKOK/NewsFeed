package org.example.newsfeed.comment.dto;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class CommentResponse {

    private final Long id;
    private final String postId;
    private final String username;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CommentResponse(Long id, String postId, String username, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.postId = postId;
        this.username = username;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}


