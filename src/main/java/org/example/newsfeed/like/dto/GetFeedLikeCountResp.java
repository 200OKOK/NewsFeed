package org.example.newsfeed.like.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetFeedLikeCountResp {

    private long feedId;
    private int count;
}
