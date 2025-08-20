package org.example.newsfeed.like.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.newsfeed.like.dto.CreateCommentLikeResp;
import org.example.newsfeed.like.dto.CreateFeedLikeResp;
import org.example.newsfeed.like.dto.GetFeedLikeCountResp;
import org.example.newsfeed.like.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/feeds/{feedId}/likes")
    public ResponseEntity<CreateFeedLikeResp> createLike(@PathVariable Long feedId,@SessionAttribute("로그인 유저") Long userId){
       return ResponseEntity.ok(likeService.createLike(userId,feedId));
    }

    @GetMapping("/feeds/{feedId}/likes")
    public ResponseEntity<GetFeedLikeCountResp> getAllLikes(@PathVariable Long feedId){

        return ResponseEntity.ok(likeService.feedLikeCount(feedId));
    }

    @PostMapping("/comments/{commentId}/likes")
    public ResponseEntity<CreateCommentLikeResp> createCommentLike(@PathVariable Long commentId,@SessionAttribute("로그인 유저") Long userId){

        return ResponseEntity.ok(likeService.createCommentLike(userId,commentId));
    }

    @GetMapping("/comments/{commentId}/likes")
    public ResponseEntity<GetFeedLikeCountResp> getAllCommentLikes(@PathVariable Long commentId){

        return ResponseEntity.ok(likeService.commentLikeCount(commentId));
    }
}
