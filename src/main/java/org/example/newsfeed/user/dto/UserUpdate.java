package org.example.newsfeed.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserUpdate {
    private String userName;
    private String nickName;
    private LocalDate birth;
}
