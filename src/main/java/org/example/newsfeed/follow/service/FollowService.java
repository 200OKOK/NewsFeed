package org.example.newsfeed.follow.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.follow.entity.Follow;
import org.example.newsfeed.follow.repository.FollowRepository;
import org.example.newsfeed.user.entity.User;
import org.example.newsfeed.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public void followUser(Long followingUserId, Long followedId){
        User follower = userRepository.findById(followedId).orElseThrow(
                () -> new IllegalArgumentException("팔로우 요청을 보낸 사용자를 찾을 수 없습니다.")
        );

        if(follower.getId().equals(followingUserId)){
            throw new IllegalArgumentException("자기 자신을 팔로우 할 수 없습니다.");
        }

        User following = userRepository.findById(followingUserId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다.")
        );

        followRepository.findByFollowerAndFollowing(follower, following).ifPresent(
                m -> {
                    throw new IllegalArgumentException("이미 팔로우 한 유저입니다.");
                }
        );

        Follow follow = new Follow(follower, following);
        followRepository.save(follow);
    }
}
