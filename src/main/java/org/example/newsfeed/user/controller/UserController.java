package org.example.newsfeed.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.user.dto.PasswordUpdate;
import org.example.newsfeed.user.dto.UserRequest;
import org.example.newsfeed.user.dto.UserResponse;
import org.example.newsfeed.user.dto.UserUpdate;
import org.example.newsfeed.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 프로필 조회
    @GetMapping("/{userId}") //프로필 조회
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable String userId
    ) {
        return ResponseEntity.ok(userService.findById(userId));
    }

    // 프로필 수정
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable String userId,
            @RequestBody UserUpdate userUpdate
    ) {
        return ResponseEntity.ok(userService.updateUser(userId,userUpdate));
    }

    // 비밀번호 수정
    @PutMapping("/{userId}/password")
    public ResponseEntity<UserResponse> updatePassword(
            @PathVariable String userId,
            @RequestBody PasswordUpdate passwordUpdate
    ) {
       return ResponseEntity.ok(userService.updatePassword(userId, passwordUpdate));
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(
            @RequestBody UserRequest request
    ) {
        userService.signUp(request);
        return ResponseEntity.ok("회원가입에 성공하셨습니다.");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody UserRequest userRequest,
            HttpServletRequest request
    ) {
        UserResponse result = userService.login(userRequest);

        HttpSession session = request.getSession();
        session.setAttribute("로그인 유저", result.getId());
        return ResponseEntity.ok("로그인에 성공하셨습니다.");
    }
}
