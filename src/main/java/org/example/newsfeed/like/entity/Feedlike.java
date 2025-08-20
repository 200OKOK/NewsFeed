package org.example.newsfeed.like.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.newsfeed.feed.entity.Feed;
import org.example.newsfeed.user.entity.User;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Feedlike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "feedId", nullable = false)
    private Feed feed;

    private LocalDateTime createAt = LocalDateTime.now();

    public Feedlike(User user, Feed feed) {
        this.user = user;
        this.feed = feed;
    }


}
