package org.example.newsfeed.feed.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.feed.dto.*;
import org.example.newsfeed.feed.service.FeedService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @PathVariable int pageNum,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<FeedPageResponseDto> result = feedService.findAllPage(pageNum, size);
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


}
