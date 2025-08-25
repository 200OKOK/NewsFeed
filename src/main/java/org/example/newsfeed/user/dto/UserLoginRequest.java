package org.example.newsfeed.user.dto;

import lombok.Getter;

@Getter
public class UserLoginRequest {
    private String userId;
    private String password;
}
