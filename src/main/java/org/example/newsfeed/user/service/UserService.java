package org.example.newsfeed.user.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.exception.ErrorCode;
import org.example.newsfeed.common.exception.MyCustomException;
import org.example.newsfeed.user.config.PasswordEncoder;
import org.example.newsfeed.user.dto.*;
import org.example.newsfeed.user.entity.User;
import org.example.newsfeed.user.entity.UserStatus;
import org.example.newsfeed.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 프로필 조회
    @Transactional(readOnly = true)
    public UserResponse findById(String userId) {
        User user =userRepository.findByUserId(userId).orElseThrow(
                () -> new MyCustomException(ErrorCode.USER_NOT_FOUND)
        );
        return new UserResponse(
                user.getId(),
                user.getUserId(),
                user.getUserName(),
                user.getNickName()
        );
    }

    // 프로필 수정
    @Transactional
    public UserResponse updateUser(String userId, UserUpdate userUpdate) {
        User user = userRepository.findByUserId(userId).orElseThrow(
                () -> new MyCustomException(ErrorCode.USER_NOT_FOUND)
        );

        if (!user.getNickName().equals(userUpdate.getNickName()) &&
                userRepository.existsByNickName(userUpdate.getNickName())) {
            throw new MyCustomException(ErrorCode.NICKNAME_ALREADY_EXISTS);
        }

        user.updateProfile(userUpdate.getUserName(), userUpdate.getNickName());

        return new UserResponse(
                user.getId(),
                user.getUserId(),
                user.getUserName(),
                user.getNickName()
        );
    }

    // 비밀번호 수정
    @Transactional
    public UserResponse updatePassword(String userId, PasswordUpdate passwordUpdate) {
        User user = userRepository.findByUserId(userId).orElseThrow(
                () -> new MyCustomException(ErrorCode.USER_NOT_FOUND)
        );

        if (!passwordEncoder.matches(passwordUpdate.getCurrentPassword(), user.getPassword())) {
            throw new MyCustomException(ErrorCode.WRONG_EMAIL_PASSWORD);
        }

        if (passwordEncoder.matches(passwordUpdate.getNewPassword(), user.getPassword())) {
            throw new MyCustomException(ErrorCode.SAME_PASSWORD);
        }

        user.updatePassword(passwordEncoder.encode(passwordUpdate.getNewPassword()));

        return new UserResponse(
                user.getId(),
                user.getUserId(),
                user.getUserName(),
                user.getNickName()
        );
    }

    // 회원가입
    @Transactional
    public void signUp(UserRequest request) {
        if (userRepository.existsByUserId(request.getUserId())) {
            throw new MyCustomException(ErrorCode.USER_ALREADY_EXISTS);
        }

        if(userRepository.existsByNickName(request.getNickName())) {
            throw new MyCustomException(ErrorCode.NICKNAME_ALREADY_EXISTS);
        }


        String encodedPw = passwordEncoder.encode(request.getPassword());

        User user = new User(
                request.getUserId(),
                request.getUserName(),
                encodedPw,
                request.getNickName()
        );
        
        userRepository.save(user);
    }

    // 로그인
    @Transactional(readOnly = true)
    public UserResponse login(UserLoginRequest loginRequest) {
        User user = userRepository.findByUserId(loginRequest.getUserId()).orElseThrow(
                () -> new MyCustomException(ErrorCode.USER_NOT_FOUND)
        );

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new MyCustomException(ErrorCode.WRONG_EMAIL_PASSWORD);
        }
        return new UserResponse(
                user.getId(),
                user.getUserId(),
                user.getUserName(),
                user.getNickName()
        );
    }

    // 회원 삭제
    @Transactional
    public void deleteuser(String userId, String password) {
        User user = userRepository.findByUserId(userId).orElseThrow(
                () -> new MyCustomException(ErrorCode.USER_NOT_FOUND)
        );

        if(user.getStatus() == UserStatus.DELETED) {
            throw new MyCustomException(ErrorCode.USER_ALREADY_DELETED);
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new MyCustomException(ErrorCode.WRONG_EMAIL_PASSWORD);
        }

        user.deleteUser();
    }
}
