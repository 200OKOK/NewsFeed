package org.example.newsfeed.follow.dto;

import lombok.Getter;

@Getter
public class FollowerResponse {
    private final Long id;
    private final String username;

    public FollowerResponse(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}