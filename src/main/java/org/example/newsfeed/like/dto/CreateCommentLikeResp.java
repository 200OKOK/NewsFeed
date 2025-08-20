package org.example.newsfeed.like.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CreateCommentLikeResp {
    private long userId;
    private long commentId;
    private LocalDateTime createdAt;
}
