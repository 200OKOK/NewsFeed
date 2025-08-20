package org.example.newsfeed.follow.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.follow.service.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follows")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{followingId}/follow")
    public ResponseEntity<String> followUser(
            @PathVariable("followingId") Long followingId) {
        // 나중에 실제 로그인된 사용자로 교체해야 함
        Long followerId = 1L;

        followService.followUser(followingId, followerId);
        return ResponseEntity.ok().body("팔로우 되었습니다.");
    }
}