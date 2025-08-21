package org.example.newsfeed.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.comment.dto.CommentCreateRequest;
import org.example.newsfeed.comment.dto.CommentResponse;
import org.example.newsfeed.comment.dto.CommentUpdateRequest;
import org.example.newsfeed.comment.entity.Comment;
import org.example.newsfeed.comment.repository.CommentRepository;
import org.example.newsfeed.common.exception.ErrorCode;
import org.example.newsfeed.common.exception.MyCustomException;
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

        if (userId == null) {
            throw new MyCustomException(ErrorCode.LOGIN_REQUIRED);
        }

        if (request.getContent() == null || request.getContent().trim().isEmpty()) {
            throw new MyCustomException(ErrorCode.COMMENT_CONTENT_REQUIRED);
        }

        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new MyCustomException(ErrorCode.FEED_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new MyCustomException(ErrorCode.USER_NOT_FOUND));

        Comment comment = new Comment(user, feed, request.getContent());
        Comment savedComment = commentRepository.save(comment);

        return CommentResponse.of(savedComment);
    }


    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByFeed(Long feedId) {

        if(!feedRepository.existsById(feedId)) {
            throw new MyCustomException(ErrorCode.FEED_NOT_FOUND);
        }

        List<Comment> comments = commentRepository.findByFeed_FeedId(feedId);

        List<CommentResponse> responses = new ArrayList<>();
        for (Comment c : comments) {
            responses.add(new CommentResponse(
                    c.getId(),
                    c.getFeed().getFeedId(),
                    c.getUser().getUserName(),
                    c.getContent(),
                    c.getCreatedAt(),
                    c.getUpdatedAt()
            ));
        }
        return responses;
    }


    @Transactional
    public CommentResponse update(Long commentId, Long userId, CommentUpdateRequest request) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new MyCustomException(ErrorCode.COMMENT_NOT_FOUND));

        if (userId == null) {
            throw new MyCustomException(ErrorCode.LOGIN_REQUIRED);
        }

        Long commentAuthorId = comment.getUser().getId();
        Long feedAuthorId = comment.getFeed().getUser().getId();

        if (!userId.equals(commentAuthorId) && !userId.equals(feedAuthorId)) {
            throw new MyCustomException(ErrorCode.UNAUTHORIZED_COMMENT_UPDATE);
        }

        String newContent = request.getContent();
        if (newContent == null || newContent.trim().isEmpty()) {
            throw new MyCustomException(ErrorCode.COMMENT_CONTENT_REQUIRED);
        }

        comment.updateContent(newContent.trim());

        return CommentResponse.of(comment);
    }


    @Transactional
    public void delete(Long commentId, Long userId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new MyCustomException(ErrorCode.COMMENT_NOT_FOUND));

        if (userId == null) {
            throw new MyCustomException(ErrorCode.LOGIN_REQUIRED);
        }

        Long commentAuthorId = comment.getUser().getId();
        Long feedAuthorId = comment.getFeed().getUser().getId();

        if(!userId.equals(commentAuthorId) && !userId.equals(feedAuthorId)) {
            throw new MyCustomException(ErrorCode.UNAUTHORIZED_COMMENT_DELETE);
        }

        commentRepository.delete(comment);
    }
}
