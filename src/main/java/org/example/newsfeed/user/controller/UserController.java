package org.example.newsfeed.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.user.dto.*;
import org.example.newsfeed.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 프로필 조회
    @GetMapping("/users/{userId}") //프로필 조회
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable String userId
    ) {
        return ResponseEntity.ok(userService.findById(userId));
    }

    // 프로필 수정
    @PutMapping("/users/{userId}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable String userId,
            @RequestBody UserUpdate userUpdate
    ) {
        return ResponseEntity.ok(userService.updateUser(userId,userUpdate));
    }

    // 비밀번호 수정
    @PutMapping("/users/{userId}/password")
    public ResponseEntity<UserResponse> updatePassword(
            @PathVariable String userId,
            @RequestBody @Valid PasswordUpdate passwordUpdate
    ) {
       return ResponseEntity.ok(userService.updatePassword(userId, passwordUpdate));
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(
            @RequestBody @Valid UserRequest request
    ) {
        userService.signUp(request);
        return ResponseEntity.ok("회원가입에 성공하셨습니다.");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody UserLoginRequest loginRequest,
            HttpServletRequest request
    ) {
        UserResponse result = userService.login(loginRequest);

        HttpSession session = request.getSession();
        session.setAttribute("로그인 유저", result.getId());
        return ResponseEntity.ok("로그인에 성공하셨습니다.");
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("로그아웃 합니다.");
    }

    // 회원 탈퇴
    @DeleteMapping
    public ResponseEntity<String> deleteUser(
            @RequestBody @Valid UserDeleteRequest request
    ) {
        userService.deleteuser(request.getUserId(), request.getPassword());
        return ResponseEntity.ok("계정을 탈퇴하셨습니다.");
    }
}
