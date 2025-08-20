package org.example.newsfeed.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND("USR-001","해당 유저를 찾을 수 없습니다."),
    WRONG_EMAIL_PASSWORD("USR-002", "이메일 혹은 비밀번호가 틀렸습니다."),
    USERNAME_SIZE("USR-003","유저명은 3글자 이상 해야합니다."),
    SELF_LIKE_NOT_ALLOWED("USR-004" ,"본인의 피드에는 좋아요를 할 수 없습니다.");
//    EVENT_NOT_FOUND("EVT-001","해당 일정을 찾을 수 없습니다"),
//    EVENT_TITLE_SIZE("EVT-002","일정 제목 길이가 올바르지 않습니다.(2~10글자 이내)");

    private final String code;
    private final String message;
}
