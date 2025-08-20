package org.example.newsfeed.comment.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.comment.dto.CommentCreateRequest;
import org.example.newsfeed.comment.dto.CommentResponse;
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
            @SessionAttribute("LOGIN_USER") Long userId,
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
}
