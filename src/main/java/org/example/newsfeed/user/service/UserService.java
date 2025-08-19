package org.example.newsfeed.user.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.user.config.PasswordEncoder;
import org.example.newsfeed.user.dto.PasswordUpdate;
import org.example.newsfeed.user.dto.UserRequest;
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

    // 프로필 조회
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

    // 프로필 수정
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

    // 비밀번호 수정
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

    // 회원가입
    @Transactional
    public void signUp(UserRequest request) {
        if (userRepository.existsByUserId(request.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
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
    public UserResponse login(UserRequest userRequest) {
        User user = userRepository.findByUserId(userRequest.getUserId()).orElseThrow(
                () -> new IllegalArgumentException("그런 id의 유저는 없습니다.")
        );

        if (!user.getPassword().equals(userRequest.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지않습니다.");
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
                () -> new IllegalArgumentException("그런 id의 유저는 없습니다.")
        );

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지않습니다.");
        }

        user.deleteUser();
    }
}
