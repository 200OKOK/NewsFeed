package org.example.newsfeed.user.dto;

import lombok.Getter;

@Getter
public class UserResponse {
    private final Long id;
    private final String userId;
    private final String username;
    private  final  String nickName;

    public UserResponse(Long id, String userId, String username, String nickName) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.nickName = nickName;
    }
}
