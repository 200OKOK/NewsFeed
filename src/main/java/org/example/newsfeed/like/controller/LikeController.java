package org.example.newsfeed.like.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.newsfeed.like.dto.CreateCommentLikeResp;
import org.example.newsfeed.like.dto.CreateFeedLikeResp;
import org.example.newsfeed.like.dto.GetCommentLikeCountResp;
import org.example.newsfeed.like.dto.GetFeedLikeCountResp;
import org.example.newsfeed.like.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LikeController {

    private final LikeService likeService;

    //게시글 좋아요 생성
    @PostMapping("/feeds/{feedId}/likes")
    public ResponseEntity<CreateFeedLikeResp> createLike(
            @PathVariable Long feedId,
            @SessionAttribute("로그인 유저") Long userId
    ){
       return ResponseEntity.ok(likeService.createLike(userId,feedId));
    }

    //게시글 좋아요 조회
    @GetMapping("/feeds/{feedId}/likes")
    public ResponseEntity<GetFeedLikeCountResp> getAllLikes(
            @PathVariable Long feedId
    ){
        return ResponseEntity.ok(likeService.feedLikeCount(feedId));
    }

    //댓글 좋아요 생성
    @PostMapping("/comments/{commentId}/likes")
    public ResponseEntity<CreateCommentLikeResp> createCommentLike(
            @PathVariable Long commentId,
            @SessionAttribute("로그인 유저") Long userId
    ){
        return ResponseEntity.ok(likeService.createCommentLike(userId,commentId));
    }

    //댓글 좋아요 조회
    @GetMapping("/comments/{commentId}/likes")
    public ResponseEntity<GetCommentLikeCountResp> getAllCommentLikes(
            @PathVariable Long commentId
    ){
        return ResponseEntity.ok(likeService.commentLikeCount(commentId));
    }
}
