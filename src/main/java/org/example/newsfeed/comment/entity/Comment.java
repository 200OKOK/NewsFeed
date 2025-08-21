package org.example.newsfeed.comment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.newsfeed.common.BaseEntity;
import org.example.newsfeed.feed.entity.Feed;
import org.example.newsfeed.user.entity.User;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "feedId", nullable = false)
    private Feed feed;

    @Column(nullable = false, length = 500)
    private String content;

    public Comment(User user, Feed feed, String content) {
        this.user = user;
        this.feed = feed;
        this.content = content;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
