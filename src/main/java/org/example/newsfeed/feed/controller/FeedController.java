package org.example.newsfeed.feed.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.feed.dto.*;
import org.example.newsfeed.feed.service.FeedService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
            @RequestBody FeedSaveRequestDto dto
    ){

        return ResponseEntity.ok(feedService.save(userId, dto));
    }

    //게시물 전체 조회
    @GetMapping("/feeds/page/{pageNum}")
    public ResponseEntity<Map<String, Object>> findAllPage(
            @SessionAttribute(name = "로그인 유저", required = false) Long userId,
            @PathVariable int pageNum,
            @RequestParam(defaultValue = "10") int size

    ) {
        Page<FeedPageResponseDto> result = feedService.findAllPage(pageNum, size, userId);
        return ResponseEntity.ok(
                Map.of(
                        "page", result.getNumber() + 1, // 1부터 시작하는 페이지 번호로 조정
                        "totalPages", result.getTotalPages(),
                        "posts", result.getContent()
                )
        );

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
    public ResponseEntity<Void> delete(
            @SessionAttribute(name = "로그인 유저", required = false) Long userId,
            @PathVariable Long feedId
    ) {
        feedService.deleteById(feedId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/feedsDate")
    public ResponseEntity<List<FeedByDateResponseDto>> getFeedByDate(@RequestParam(value = "searchStartDate", required = false, defaultValue = "19000101") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate searchStartDate,
                                                 @RequestParam(value = "searchEndDate", required = false, defaultValue = "99991231") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate searchEndDate

    ) {
        List<FeedByDateResponseDto> feedList = feedService.getFeedByDate(searchStartDate, searchEndDate);
        return ResponseEntity.ok(feedList);
    }


}