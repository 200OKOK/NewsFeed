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

    @PostMapping("/feeds/{feedId}/comments")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long feedId,
            @SessionAttribute(name = "로그인 유저") Long userId,
            @Valid @RequestBody CommentCreateRequest request
    ) {
        return ResponseEntity.ok(commentService.create(feedId, userId, request));

//        CommentResponse res = commentService.create(feedId, userId, request);
//        URI location = URI.create("/comments/" + res.id());
//        return ResponseEntity.created(location).body(res);  // 201 + location
    }

    @GetMapping("/feeds/{feedId}/comments")
    public ResponseEntity<List<CommentResponse>> getAllComments(
            @PathVariable Long feedId
    ) {
        List<CommentResponse> responses = commentService.getCommentsByFeed(feedId);
        return ResponseEntity.ok(responses);
    }


    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long commentId,
            @SessionAttribute(name = "로그인 유저") Long userId,
            @RequestBody CommentUpdateRequest request
    ) {
        CommentResponse updatedComment = commentService.update(commentId, userId, request);
        return ResponseEntity.ok(updatedComment);
    }


    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponse> deleteComment(
            @PathVariable Long commentId,
            @SessionAttribute(name = "로그인 유저") Long userId
    ) {
        commentService.delete(commentId, userId);
        return ResponseEntity.noContent().build();
    }
}
