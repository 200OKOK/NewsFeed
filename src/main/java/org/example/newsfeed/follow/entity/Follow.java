package org.example.newsfeed.follow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.newsfeed.user.entity.User;

@Getter
@Entity
@NoArgsConstructor
public class Follow{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "followingId", nullable = false)
    private User following; // 팔로우 대상 유저

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "followerId", nullable = false)
    private User follower; // 팔로우 하는 유저

    public Follow(User follower, User following) {
        this.follower = follower;
        this.following = following;
    }


}
