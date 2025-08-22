package org.example.newsfeed.feed.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.feed.dto.*;
import org.example.newsfeed.feed.service.FeedService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    // 게시물 생성
    @PostMapping("/feeds")
    public ResponseEntity<FeedSaveResponseDto> save(
            @SessionAttribute(name = "로그인 유저", required = false) Long userId,
            @RequestBody @Valid FeedSaveRequestDto dto
    ){

        return ResponseEntity.ok(feedService.save(userId, dto));
    }

    //게시물 전체 조회
    @GetMapping("/feeds/page/{pageNum}")
    public ResponseEntity<PageResponseDto<PageResponseDto>> findAllPage(
            @SessionAttribute(name = "로그인 유저", required = false) Long userId,
            @PathVariable int pageNum,
            @RequestParam(defaultValue = "10") int size

    ) {
        PageResponseDto<PageResponseDto> result = feedService.findAllPage(pageNum, size, userId);
        return ResponseEntity.ok(result);
    }

    // 게시물 수정
    @PutMapping("/feeds/{feedId}")
    public ResponseEntity<FeedUpdateResponseDto> update(
            @SessionAttribute(name = "로그인 유저", required = false) Long userId,
            @PathVariable Long feedId,
            @RequestBody FeedUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(feedService.update(feedId, userId, dto));
    }

    // 게시물 삭제
    @DeleteMapping("/feeds/{feedId}")
    public ResponseEntity<String> delete(
            @SessionAttribute(name = "로그인 유저", required = false) Long userId,
            @PathVariable Long feedId
    ) {
        String message = feedService.deleteById(feedId, userId);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/feedsDate")
    public ResponseEntity<List<FeedByDateResponseDto>> getFeedByDate(@RequestParam(value = "searchStartDate", required = false, defaultValue = "19000101") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate searchStartDate,
                                                                     @RequestParam(value = "searchEndDate", required = false, defaultValue = "99991231") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate searchEndDate

    ) {
        List<FeedByDateResponseDto> feedList = feedService.getFeedByDate(searchStartDate, searchEndDate);
        return ResponseEntity.ok(feedList);
    }

    // 팔로우한 유저의 게시물 전체 조회
    @GetMapping("/feeds/following")
    public ResponseEntity<List<FeedResponseDto>> getFollowingUsersPosts(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("로그인 유저");

        List<FeedResponseDto> feeds = feedService.getFollowingFeeds(userId);
        return ResponseEntity.ok(feeds);
    }

}