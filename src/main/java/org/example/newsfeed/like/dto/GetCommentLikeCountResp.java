package org.example.newsfeed.like.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetCommentLikeCountResp {

    private long commentId;
    private int count;
}
