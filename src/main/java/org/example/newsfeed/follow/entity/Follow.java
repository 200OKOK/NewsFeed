package org.example.newsfeed.follow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.newsfeed.feed.entity.Feed;
import org.example.newsfeed.user.entity.User;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Follow {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "followingId")
    private User following;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "followerId")
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "feedId", nullable = false)
    private Feed feed;

    private LocalDateTime createAt;

    public Follow(User follower, User following) {
        this.follower = follower;
        this.following = following;
    }

}
