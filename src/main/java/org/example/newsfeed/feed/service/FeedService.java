package org.example.newsfeed.feed.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.exception.MyCustomException;
import org.example.newsfeed.feed.dto.*;
import org.example.newsfeed.feed.entity.Feed;
import org.example.newsfeed.feed.repository.FeedRepository;
import org.example.newsfeed.follow.dto.FollowingResponse;
import org.example.newsfeed.follow.service.FollowService;
import org.example.newsfeed.user.entity.User;
import org.example.newsfeed.user.entity.UserStatus;
import org.example.newsfeed.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


import static org.example.newsfeed.common.exception.ErrorCode.*;


@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final FollowService followService;

    // 게시글 생성
    @Transactional
    public FeedSaveResponseDto save(Long userId, FeedSaveRequestDto dto) {

        validateLogin(userId);

        User user = getActiveUser(userId);

        Feed feed = new Feed(user, dto.getTitle(), dto.getContent());
        if (dto.getTitle() == null || dto.getTitle().trim().isEmpty() ||
                dto.getContent() == null || dto.getContent().trim().isEmpty()) {
            throw new MyCustomException(TITLE_OR_CONTENT_REQUIRED);
        }

        feedRepository.save(feed);

        return new FeedSaveResponseDto(
                feed.getFeedId(),
                feed.getUser().getUserId(),
                feed.getTitle(),
                feed.getContent(),
                feed.getCreatedAt(),
                feed.getUpdatedAt()
        );
    }

    //게시글 전체 조회
    @Transactional(readOnly = true)
    public Page<FeedPageResponseDto> findAllPage(int page, int size, Long userId) {
        validateLogin(userId);
        int adjustedPage = (page > 0) ? page - 1 : 0;
        PageRequest pageable = PageRequest.of(adjustedPage, size, Sort.by("createdAt").descending());
        Page<Feed> feedPage = feedRepository.findAll(pageable);

        return feedPage.map(feed -> new FeedPageResponseDto(
                feed.getFeedId(),
                feed.getTitle(),
                feed.getContent(),
                feed.getCreatedAt(),
                feed.getUpdatedAt(),
                feed.getUser().getUserName()
        ));
    }

    // 게시글 수정
    @Transactional
    public FeedUpdateResponseDto update(Long feedId, Long userId, FeedUpdateRequestDto dto) {
        validateLogin(userId);

        User user = getActiveUser(userId);

        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new MyCustomException(FEED_NOT_FOUND));

        if (!user.getId().equals(feed.getUser().getId())) {
            throw new MyCustomException(INVALID_FEED_OWNER);
        }

        feed.update(dto.getTitle(), dto.getContent());
        return new FeedUpdateResponseDto(
                feed.getFeedId(),
                feed.getUser().getUserId(),
                feed.getTitle(),
                feed.getContent(),
                feed.getCreatedAt(),
                feed.getUpdatedAt()
        );
    }

    // 게시글 삭제
    @Transactional
    public void deleteById(Long feedId, Long userId) {
        validateLogin(userId);
        User user = getActiveUser(userId);
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new MyCustomException(FEED_NOT_FOUND));

        if (!user.getId().equals(feed.getUser().getId())) {
            throw new MyCustomException(UNAUTHORIZED_FEED_DELETE);
        }

        feedRepository.delete(feed);
    }

    // 사용자 검증 메서드(유저 존재 여부, 유저 활성화 여부 확인)
    private User getActiveUser(Long userId) {
        return userRepository.findById(userId)
                .filter(user -> user.getStatus() == UserStatus.ACTIVE)
                .orElseThrow(() -> new MyCustomException(USER_NOT_FOUND));
    }

    //로그인을 안 했을 때 예외 발생
    private void validateLogin(Long userId) {
        if (userId == null) {
            throw new MyCustomException(LOGIN_REQUIRED);
        }
    }

    public List<FeedByDateResponseDto> getFeedByDate(LocalDate searchStartDate, LocalDate searchEndDate) {


        //엔티티가 LocalDateTIme이라서 맞춰줘야함
        LocalDateTime start = searchStartDate.atStartOfDay();           // 00:00:00
        LocalDateTime end = searchEndDate.atTime(LocalTime.MAX);        // 23:59:59.999999999
        List<Feed> feedList = feedRepository.findByCreatedAtBetween(start,end);

        return feedList.stream()
                .map(feed -> new FeedByDateResponseDto(
                        feed.getFeedId(),
                        feed.getUser().getUserId(),
                        feed.getTitle(),
                        feed.getContent(),
                        feed.getCreatedAt(),
                        feed.getUpdatedAt()
                )).toList();
    }


    // 팔로우한 유저들의 게시물 전체 조회
    @Transactional(readOnly = true)
    public List<FeedResponseDto> getFollowingFeeds(Long userId) {
        // 1. 현재 로그인한 유저가 팔로우하는 모든 유저의 ID 목록을 가져옴
        List<FollowingResponse> followingList = followService.getFollowingUsers(userId);

        List<Long> followingUserIds = followingList.stream()
                .map(FollowingResponse::getId)
                .collect(Collectors.toList());

        // 2. 팔로우한 유저가 없다면 빈 목록을 반환
        if (followingUserIds.isEmpty()) {
            return Collections.emptyList();
        }

        // 3. 팔로우한 유저들의 ID를 사용하여 게시물을 최신순으로 조회
        List<Feed> feeds = feedRepository.findAllByUser_UserIdInOrderByCreatedAtDesc(followingUserIds);

        return feeds.stream()
                .map(feed -> new FeedResponseDto(
                        feed.getFeedId(),
                        feed.getUser().getUserId(),
                        feed.getTitle(),
                        feed.getContent(),
                        feed.getCreatedAt(),
                        feed.getUpdatedAt()))
                .collect(Collectors.toList());
    }
}