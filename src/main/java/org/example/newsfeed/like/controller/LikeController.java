package org.example.newsfeed.like.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.like.dto.CreateFeedLikeResp;
import org.example.newsfeed.like.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/feeds/{feedId}/likes/{userId}") //유저아이디는 sessionAttribute로 받을거임
    public ResponseEntity<CreateFeedLikeResp> createLike(@PathVariable Long feedId,@PathVariable Long userId /*@SessionAttribute("USERID") Long userId*/){

        return ResponseEntity.ok(likeService.createLike(userId,feedId));
    }
}
