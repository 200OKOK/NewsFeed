package org.example.newsfeed.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordUpdate {
    private String currentPassword;
    private String newPassword;
}
