package org.example.newsfeed.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.user.dto.PasswordUpdate;
import org.example.newsfeed.user.dto.UserResponse;
import org.example.newsfeed.user.dto.UserUpdate;
import org.example.newsfeed.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}") //프로필 조회
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable String userId
    ) {
        return ResponseEntity.ok(userService.findById(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable String userId,
            @RequestBody UserUpdate userUpdate
    ) {
        return ResponseEntity.ok(userService.updateUser(userId,userUpdate));
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<UserResponse> updatePassword(
            @PathVariable String userId,
            @RequestBody PasswordUpdate passwordUpdate
    ) {
       return ResponseEntity.ok(userService.updatePassword(userId, passwordUpdate));
    }
}
