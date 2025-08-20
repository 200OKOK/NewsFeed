package org.example.newsfeed.follow.dto;

import lombok.Getter;
import org.example.newsfeed.follow.entity.Follow;

@Getter
public class FollowResponse {
    private final Long id;
    private final Long followingId;
    private final Long followerId;
    private final String message;

    public FollowResponse(Follow follow) {
        this.id = follow.getId();
        this.followingId = follow.getFollowing().getId();
        this.followerId = follow.getFollower().getId();
        this.message = "팔로우 성공적으로 추가되었습니다.";
    }
}
