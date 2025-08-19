package org.example.newsfeed.user.dto;

import lombok.Getter;

@Getter
public class UserRequest {
    private String userId;
    private String userName;
    private String password;
    private String nickName;
}
