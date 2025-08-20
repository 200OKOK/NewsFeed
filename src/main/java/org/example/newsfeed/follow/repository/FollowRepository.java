package org.example.newsfeed.follow.repository;

import org.example.newsfeed.follow.entity.Follow;
import org.example.newsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow,Long> {
    List<Follow> findAllByFollower(User followers);
}
