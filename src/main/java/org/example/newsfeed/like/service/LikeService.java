package org.example.newsfeed.like.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.newsfeed.feed.entity.Feed;
import org.example.newsfeed.feed.repository.FeedRepository;
import org.example.newsfeed.like.dto.CreateFeedLikeResp;
import org.example.newsfeed.like.entity.Feedlike;
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

    @Transactional
    public CreateFeedLikeResp createLike(Long userId,Long feedId) {

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
}
