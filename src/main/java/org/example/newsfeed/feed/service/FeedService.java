package org.example.newsfeed.feed.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.exception.MyCustomException;
import org.example.newsfeed.feed.dto.*;
import org.example.newsfeed.feed.entity.Feed;
import org.example.newsfeed.feed.repository.FeedRepository;
import org.example.newsfeed.user.entity.User;
import org.example.newsfeed.user.entity.UserStatus;
import org.example.newsfeed.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.example.newsfeed.common.exception.ErrorCode.*;


@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final UserRepository userRepository;

    // 게시글 생성
    @Transactional
    public FeedSaveResponseDto save(Long userId, FeedSaveRequestDto dto) {

        validateLogin(userId);

        User user = getActiveUser(userId);

        Feed feed = new Feed(user, dto.getTitle(), dto.getContent());

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
}