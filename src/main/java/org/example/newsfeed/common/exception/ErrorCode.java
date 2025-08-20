package org.example.newsfeed.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND("USR-001","해당 유저를 찾을 수 없습니다."),
    WRONG_EMAIL_PASSWORD("USR-002", "이메일 혹은 비밀번호가 틀렸습니다."),
    USERNAME_SIZE("USR-003","유저명은 3글자 이상 해야합니다."),
    SELF_FEED_LIKE_NOT_ALLOWED("USR-004" ,"본인의 피드에는 좋아요를 할 수 없습니다."),
    SELF_COMMENT_LIKE_NOT_ALLOWED("USR-005" ,"본인의 피드에는 좋아요를 할 수 없습니다."),
    USER_ALREADY_EXISTS("USR-006","이미 존재하는 이메일입니다."),
    NICKNAME_ALREADY_EXISTS("USR-007","이미 존재하는 닉네임입니다."),
    USER_ALREADY_DELETED("USR-008", "이미 탈퇴한 계정입니다."),

    FEED_NOT_FOUND("FED-001","해당 피드를 찾을 수 없습니다."),



    COMMENT_NOT_FOUND("CMT-001","해당 댓글 찾을 수 없습니다."),



    PASSWORD_TOO_SHORT("PWD-001", "비밀번호는 최소 8자리 이상이어야 합니다."),
    PASSWORD_POLICY_VIOLATION("PWD-002", "비밀번호는 대문자, 소문자, 숫자, 특수문자를 각각 최소 1글자 포함해야 합니다."),
    SAME_PASSWORD("PWD-003","새 비밀번호는 현재 비밀번호와 같습니다.");
    
    

    FOLLOWUSER_NOT_FOUND("FLW-001", "팔로우 요청을 보낸 사용자를 찾을 수 없습니다."),
    SELF_FOLLOW_NOT_ALLOWED("FLW-002", "자기 자신을 팔로우 할 수 없습니다."),
    ALREADY_FOLLOW("FLW-003", "이미 팔로우 한 유저입니다."),
    UNFOLLOWUSER_NOT_FOUND("FLW-004", "팔로우 취소 요청을 보낸 사용자를 찾을 수 없습니다."),
    FOLLOW_RELATIONSHIP_NOT_EXIST("FLW-005", "팔로우 관계가 존재하지 않습니다.");



//    EVENT_NOT_FOUND("EVT-001","해당 일정을 찾을 수 없습니다"),
//    EVENT_TITLE_SIZE("EVT-002","일정 제목 길이가 올바르지 않습니다.(2~10글자 이내)");

    private final String code;
    private final String message;
}
