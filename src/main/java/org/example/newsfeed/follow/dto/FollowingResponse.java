package org.example.newsfeed.follow.dto;

import lombok.Getter;

@Getter
public class FollowingResponse {
    private final Long id;
    private final String username;

    public FollowingResponse(Long id, String username){
        this.id = id;
        this.username = username;
    }
}
