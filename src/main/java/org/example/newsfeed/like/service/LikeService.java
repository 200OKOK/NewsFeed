package org.example.newsfeed.like.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.newsfeed.comment.entity.Comment;
import org.example.newsfeed.comment.repository.CommentRepository;
import org.example.newsfeed.feed.entity.Feed;
import org.example.newsfeed.feed.repository.FeedRepository;
import org.example.newsfeed.like.dto.CreateCommentLikeResp;
import org.example.newsfeed.like.dto.CreateFeedLikeResp;
import org.example.newsfeed.like.dto.GetFeedLikeCountResp;
import org.example.newsfeed.like.entity.CommentLike;
import org.example.newsfeed.like.entity.Feedlike;
import org.example.newsfeed.like.repository.CommentLikeRepository;
import org.example.newsfeed.like.repository.TableLikeRepository;
import org.example.newsfeed.user.entity.User;
import org.example.newsfeed.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {

    private final UserRepository userRepository;
    private final FeedRepository feedRepository;
    private final TableLikeRepository tableLikeRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public CreateFeedLikeResp createLike(Long userId,Long feedId) {

        log.info("게시글 ID : {} , 유저 ID : {}", feedId, userId);

        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new EntityNotFoundException("Feed not found"));


        boolean exists = tableLikeRepository.existsByUser_IdAndFeed_FeedId(userId,feedId);

        if(!exists){
            Feedlike feedLike = tableLikeRepository.save(new Feedlike(user,feed));
            return  new CreateFeedLikeResp(user.getId(),feed.getFeedId(),feedLike.getCreateAt());
        }else{
            tableLikeRepository.deleteByUser_IdAndFeed_FeedId(userId,feedId);
              return null;
        }

    }

    @Transactional(readOnly = true)
    public GetFeedLikeCountResp feedLikeCount(Long feedId) {
        int count = tableLikeRepository.countFeedByFeed_FeedId(feedId);
        return new GetFeedLikeCountResp(feedId,count);
    }

    @Transactional
    public CreateCommentLikeResp createCommentLike(Long userId, Long commentId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        boolean exists = commentLikeRepository.existsByUser_IdAndComment_Id(userId,commentId);

        if(!exists){
            CommentLike commentLike = commentLikeRepository.save(new CommentLike(user,comment));
            return new CreateCommentLikeResp(user.getId(),comment.getId(),commentLike.getCreateAt());
        }else{
            commentLikeRepository.deleteByUser_IdAndComment_Id(userId,commentId);
            return null;
        }
    }
}
