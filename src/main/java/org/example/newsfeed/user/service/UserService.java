package org.example.newsfeed.user.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.user.config.PasswordEncoder;
import org.example.newsfeed.user.dto.PasswordUpdate;
import org.example.newsfeed.user.dto.UserResponse;
import org.example.newsfeed.user.dto.UserUpdate;
import org.example.newsfeed.user.entity.User;
import org.example.newsfeed.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserResponse findById(String userId) {
        User user =userRepository.findByUserId(userId).orElseThrow(
                () -> new IllegalArgumentException("그런 id의 유저는 없습니다.")
        );
        return new UserResponse(
                user.getId(),
                user.getUserId(),
                user.getUserName(),
                user.getNickName()
        );
    }

    @Transactional
    public UserResponse updateUser(String userId, UserUpdate userUpdate) {
        User user = userRepository.findByUserId(userId).orElseThrow(
                () -> new IllegalArgumentException("그런 id의 유저는 없습니다.")
        );
        user.updateProfile(userUpdate.getUserName(), userUpdate.getNickName());

        return new UserResponse(
                user.getId(),
                user.getUserId(),
                user.getUserName(),
                user.getNickName()
        );
    }

    @Transactional
    public UserResponse updatePassword(String userId, PasswordUpdate passwordUpdate) {
        User user = userRepository.findByUserId(userId).orElseThrow(
                () -> new IllegalArgumentException("그런 id의 유저는 없습니다.")
        );

        if (!passwordEncoder.matches(passwordUpdate.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 올바르지 않습니다.");
        }

        if (passwordEncoder.matches(passwordUpdate.getNewPassword(), user.getPassword())) {
            throw new IllegalArgumentException("새 비밀번호는 현재 비밀번호와 같습니다.");
        }

        user.updatePassword(passwordEncoder.encode(passwordUpdate.getNewPassword()));

        return new UserResponse(
                user.getId(),
                user.getUserId(),
                user.getUserName(),
                user.getNickName()
        );
    }
}
