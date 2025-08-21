package org.example.newsfeed.feed.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.feed.dto.*;
import org.example.newsfeed.feed.service.FeedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    // 게시물 생성
    @PostMapping("/feeds")
    public ResponseEntity<FeedSaveResponseDto> save(
            @SessionAttribute(name = "로그인 유저", required = false) Long userId,
            @RequestBody FeedSaveRequestDto dto
    ){

        return ResponseEntity.ok(feedService.save(userId, dto));
    }

    // 게시물 전체 조회
    @GetMapping("/feeds")
    public ResponseEntity<List<FeedResponseDto>> findAll() {
        return ResponseEntity.ok(feedService.findAll());
    }

    // 게시물 수정
    @PutMapping("/feeds/{feedId}")
    public ResponseEntity<FeedUpdateResponseDto> update(
            @SessionAttribute(name = "로그인 유저") Long userId,
            @PathVariable Long feedId,
            @RequestBody FeedUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(feedService.update(feedId, userId, dto));
    }

    // 게시물 삭제
    @DeleteMapping("/feeds/{feedId}")
    public ResponseEntity<Void> delete(
            @SessionAttribute(name = "로그인 유저") Long userId,
            @PathVariable Long feedId
    ) {
        feedService.deleteById(feedId, userId);
        return ResponseEntity.ok().build();
    }

    // 팔로우한 유저의 게시물 전체 조회
    @GetMapping("/feeds/following")
    public ResponseEntity<List<FeedResponseDto>> getFollowingUsersPosts(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("로그인 유저") == null) {
            return ResponseEntity.status(401).body(null);
        }
        Long userId = (Long) session.getAttribute("로그인 유저");

        List<FeedResponseDto> feeds = feedService.getFollowingFeeds(userId);
        return ResponseEntity.ok(feeds);
    }

    //페이징
//    @GetMapping("/feeds")
//    public ResponseEntity<List<FeedResponse>> getPagedAll(
//            @RequestParam int page,
//            @RequestParam int size
//    ){
//        return ResponseEntity.ok(feedService.findAll(int page, int size));
//    }
}
