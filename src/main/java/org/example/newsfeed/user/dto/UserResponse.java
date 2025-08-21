package org.example.newsfeed.user.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserResponse {
    private final Long id;
    private final String userId;
    private final String username;
    private  final  String nickName;
    private final LocalDate birth;

    public UserResponse(Long id, String userId, String username, String nickName, LocalDate birth) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.nickName = nickName;
        this.birth = birth;
    }
}
