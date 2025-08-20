package org.example.newsfeed.follow.service;

import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    // 팔로우 기능
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

    // 팔로우 취소 기능
    @Transactional
    public void unfollowUser(Long followingUserId, Long followerId) {
        User follower = userRepository.findById(followerId).orElseThrow(
                () -> new IllegalArgumentException("팔로우 취소 요청을 보낸 사용자를 찾을 수 없습니다.")
        );

        User following = userRepository.findById(followingUserId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

        Follow follow = followRepository.findByFollowerAndFollowing(follower, following)
                .orElseThrow(() -> new IllegalArgumentException("팔로우 관계가 존재하지 않습니다."));

        followRepository.delete(follow);
    }

    // 팔로우한 유저 목록 전체 조회 기능
    @Transactional(readOnly = true)
    public List<FollowingResponse> getFollowingUsers(Long followerId) {
        User follower = userRepository.findById(followerId).orElseThrow(
                () -> new IllegalArgumentException("유저를 찾을 수 없습니다.")
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
                () -> new IllegalArgumentException("유저를 찾을 수 없습니다.")
        );

        User following = userRepository.findById(followingId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다.")
        );

        followRepository.findByFollowerAndFollowing(follower, following)
                .orElseThrow(() -> new IllegalArgumentException("팔로우 관계가 존재하지 않습니다."));

        return new FollowingResponse(following.getId(), following.getUserName());
    }

    // 나를 팔로우 하는 유저 목록 전체 조회
    @Transactional(readOnly = true)
    public List<FollowerResponse> getFollowerUsers(Long followingId) {
        User following = userRepository.findById(followingId).orElseThrow(
                () -> new IllegalArgumentException("유저를 찾을 수 없습니다.")
        );

        List<Follow> followers = followRepository.findAllByFollowing(following);

        return followers.stream()
                .map(follow -> new FollowerResponse(
                        follow.getFollower().getId(),
                        follow.getFollower().getUserName()))
                .collect(Collectors.toList());
    }
}
