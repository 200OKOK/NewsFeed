package org.example.newsfeed.follow.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

    @PostMapping("/{followingId}")
    public ResponseEntity<String> followUser(
            @PathVariable("followingId") Long followingId,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("로그인 유저") == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        Long followerId = (Long) session.getAttribute("로그인 유저");

        followService.followUser(followingId, followerId);
        return ResponseEntity.ok().body("팔로우 되었습니다.");
    }
}