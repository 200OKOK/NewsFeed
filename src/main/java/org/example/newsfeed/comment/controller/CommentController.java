package org.example.newsfeed.comment.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.comment.dto.CommentCreateRequest;
import org.example.newsfeed.comment.dto.CommentResponse;
import org.example.newsfeed.comment.dto.CommentUpdateRequest;
import org.example.newsfeed.comment.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //댓글 생성
    @PostMapping("/feeds/{feedId}/comments")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long feedId,
            @SessionAttribute("로그인 유저") Long userId,
            @Valid @RequestBody CommentCreateRequest request
    ) {

        CommentResponse response = commentService.create(feedId, userId, request);
        URI location = URI.create("/comments/" + response.getId());
        return ResponseEntity.created(location).body(response);
    }

    //댓글 조회
    @GetMapping("/feeds/{feedId}/comments")
    public ResponseEntity<List<CommentResponse>> getAllComments(
            @PathVariable Long feedId
    ) {
        List<CommentResponse> responses = commentService.getCommentsByFeed(feedId);
        return ResponseEntity.ok(responses);
    }

    //댓글 수정
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long commentId,
            @SessionAttribute("로그인 유저") Long userId,
            @RequestBody CommentUpdateRequest request
    ) {
        CommentResponse updatedComment = commentService.update(commentId, userId, request);
        return ResponseEntity.ok(updatedComment);
    }

    //댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponse> deleteComment(
            @PathVariable Long commentId,
            @SessionAttribute("로그인 유저") Long userId
    ) {
        commentService.delete(commentId, userId);
        return ResponseEntity.noContent().build();
    }
}
