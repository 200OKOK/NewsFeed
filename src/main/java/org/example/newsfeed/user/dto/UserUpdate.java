package org.example.newsfeed.user.dto;

import lombok.Getter;
import java.time.LocalDate;

@Getter
public class UserUpdate {
    private String userName;
    private String nickName;
    private LocalDate birth;
}
