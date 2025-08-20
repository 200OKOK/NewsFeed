package org.example.newsfeed.feed.service;

import lombok.RequiredArgsConstructor;
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


@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final UserRepository userRepository;

    // 게시글 생성
    @Transactional
    public FeedSaveResponseDto save(Long userId, FeedSaveRequestDto dto) {
        User user = getActiveUser(userId);

        if(userId==null){
            throw new IllegalStateException("로그인 후 이용 가능합니다.");
        }

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

    // 게시글 수정
    @Transactional
    public FeedUpdateResponseDto update(Long feedId, Long userId, FeedUpdateRequestDto dto) {
        User user = getActiveUser(userId);
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));

        if (!user.getId().equals(feed.getUser().getId())) {
            throw new IllegalArgumentException("본인이 작성한 게시물만 수정할 수 있습니다.");
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
        User user = getActiveUser(userId);
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

        if (!user.getId().equals(feed.getUser().getId())) {
            throw new IllegalArgumentException("작성자만 게시물을 삭제할 수 있습니다.");
        }

        feedRepository.delete(feed);
    }

    // 사용자 검증 메서드
    private User getActiveUser(Long userId) {
        return userRepository.findById(userId)
                .filter(user -> user.getStatus() == UserStatus.ACTIVE)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않거나 탈퇴한 사용자입니다."));
    }

    //게시글 전체 조회
    @Transactional(readOnly = true)
    public Page<FeedPageResponseDto> findAllPage(int page, int size) {
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
}