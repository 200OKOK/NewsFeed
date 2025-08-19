package org.example.newsfeed.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDeleteRequest {
    private String userId;
    private String password;
}
