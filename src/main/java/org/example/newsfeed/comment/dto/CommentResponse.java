package org.example.newsfeed.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.newsfeed.comment.entity.Comment;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponse {

    private final Long id;
    private final Long feedId;
    private final String username;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static CommentResponse of(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getFeed().getFeedId(),
                comment.getUser().getUserName(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}


