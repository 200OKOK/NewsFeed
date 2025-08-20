package org.example.newsfeed.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.comment.dto.CommentCreateRequest;
import org.example.newsfeed.comment.dto.CommentResponse;
import org.example.newsfeed.comment.entity.Comment;
import org.example.newsfeed.comment.repository.CommentRepository;
import org.example.newsfeed.feed.entity.Feed;
import org.example.newsfeed.feed.repository.FeedRepository;
import org.example.newsfeed.user.entity.User;
import org.example.newsfeed.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final FeedRepository feedRepository;

    @Transactional
    public CommentResponse create(Long feedId, Long userId, CommentCreateRequest request) {

        if (request.getContent() == null || request.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("댓글 내용을 입력해 주세요.");
        }

        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 피드입니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("로그인이 필요합니다."));

        Comment comment = new Comment(user, feed, request.getContent());
        Comment savedComment = commentRepository.save(comment);

        return new CommentResponse(
                savedComment.getId(),
                savedComment.getFeed().getFeedId(),    // feed.getId(),
                savedComment.getUser().getUserName(),  // user.getUsername() 으로 생략 가능 (같은 객체를 참조하기 때문)
                savedComment.getContent(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByFeed(Long feedId) {
        feedRepository.findById(feedId)
                .orElseThrow(() -> new IllegalArgumentException("Feed Not Found"));

        List<Comment> comments = commentRepository.findByFeed_FeedId(feedId);

        List<CommentResponse> responses = new ArrayList<>();
        for (Comment c : comments) {
            responses.add(new CommentResponse(
                    c.getId(),
                    c.getFeed().getFeedId(),
                    c.getUser().getUserName(),
                    c.getContent(),
                    c.getCreatedAt(),
                    c.getModifiedAt()
            ));
        }
        return responses;
    }



}
