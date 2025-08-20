package org.example.newsfeed.like.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.like.dto.CreateCommentLikeResp;
import org.example.newsfeed.like.dto.CreateFeedLikeResp;
import org.example.newsfeed.like.dto.GetFeedLikeCountResp;
import org.example.newsfeed.like.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/feeds/{feedId}/likes/{userId}") //유저아이디는 sessionAttribute로 받을거임
    public ResponseEntity<CreateFeedLikeResp> createLike(@PathVariable Long feedId,@PathVariable Long userId /*@SessionAttribute("USERID") Long userId*/){

        return ResponseEntity.ok(likeService.createLike(userId,feedId));
    }

    @GetMapping("/feeds/{feedId}/likes")
    public ResponseEntity<GetFeedLikeCountResp> getAllLikes(@PathVariable Long feedId){

        return ResponseEntity.ok(likeService.feedLikeCount(feedId));
    }

    @PostMapping("/comments/{commentId}/likes/{userId}") //유저아이디는 sessionAttribute로 받을거임
    public ResponseEntity<CreateCommentLikeResp> createCommentLike(@PathVariable Long commentId, @PathVariable Long userId /*@SessionAttribute("USERID") Long userId*/){

        return ResponseEntity.ok(likeService.createCommentLike(userId,commentId));
    }
}
