package org.example.newsfeed.follow.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.exception.MyCustomException;
import org.example.newsfeed.follow.dto.FollowerResponse;
import org.example.newsfeed.follow.dto.FollowingResponse;
import org.example.newsfeed.follow.entity.Follow;
import org.example.newsfeed.follow.repository.FollowRepository;
import org.example.newsfeed.user.entity.User;
import org.example.newsfeed.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import static org.example.newsfeed.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    // 팔로우 기능
    @Transactional
    public void followUser(Long followingUserId, Long followedId){
        User follower = userRepository.findById(followedId).orElseThrow(
                () -> new MyCustomException(FOLLOWUSER_NOT_FOUND)
        );

        if(follower.getId().equals(followingUserId)){
            throw new MyCustomException(SELF_FOLLOW_NOT_ALLOWED);
        }

        User following = userRepository.findById(followingUserId).orElseThrow(
                () -> new MyCustomException(USER_NOT_FOUND)
        );

        followRepository.findByFollowerAndFollowing(follower, following).ifPresent(
                m -> {
                    throw new MyCustomException(ALREADY_FOLLOW);
                }
        );

        Follow follow = new Follow(follower, following);
        followRepository.save(follow);
    }

    // 팔로우 취소 기능
    @Transactional
    public void unfollowUser(Long followingUserId, Long followerId) {
        User follower = userRepository.findById(followerId).orElseThrow(
                () -> new MyCustomException(UNFOLLOWUSER_NOT_FOUND)
        );

        User following = userRepository.findById(followingUserId)
                .orElseThrow(() -> new MyCustomException(USER_NOT_FOUND));

        Follow follow = followRepository.findByFollowerAndFollowing(follower, following)
                .orElseThrow(() -> new MyCustomException(FOLLOW_RELATIONSHIP_NOT_EXIST));

        followRepository.delete(follow);
    }

    // 팔로우한 유저 목록 전체 조회 기능
    @Transactional(readOnly = true)
    public List<FollowingResponse> getFollowingUsers(Long followerId) {
        User follower = userRepository.findById(followerId).orElseThrow(
                () -> new MyCustomException(USER_NOT_FOUND)
        );

        List<Follow> follows = followRepository.findAllByFollower(follower);

        return follows.stream()
                .map(follow -> new FollowingResponse(
                        follow.getFollowing().getId(),
                        follow.getFollowing().getUserName()))
                .collect(Collectors.toList());
    }

    // ID를 통해 팔로우한 유저 조회
    @Transactional(readOnly = true)
    public FollowingResponse getFollowingUserById(Long followingId, Long followerId) {
        User follower = userRepository.findById(followerId).orElseThrow(
                () -> new MyCustomException(USER_NOT_FOUND)
        );

        User following = userRepository.findById(followingId).orElseThrow(
                () -> new MyCustomException(USER_NOT_FOUND)
        );

        followRepository.findByFollowerAndFollowing(follower, following)
                .orElseThrow(() -> new MyCustomException(FOLLOW_RELATIONSHIP_NOT_EXIST));

        return new FollowingResponse(following.getId(), following.getUserName());
    }

    // 나를 팔로우 하는 유저 목록 전체 조회
    @Transactional(readOnly = true)
    public List<FollowerResponse> getFollowerUsers(Long followingId) {
        User following = userRepository.findById(followingId).orElseThrow(
                () -> new MyCustomException(USER_NOT_FOUND)
        );

        List<Follow> followers = followRepository.findAllByFollowing(following);

        return followers.stream()
                .map(follow -> new FollowerResponse(
                        follow.getFollower().getId(),
                        follow.getFollower().getUserName()))
                .collect(Collectors.toList());
    }
}
