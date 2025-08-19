package org.example.newsfeed.like.entity;

import jakarta.persistence.*;
import org.example.newsfeed.comment.entity.Comment;
import org.example.newsfeed.feed.entity.Feed;
import org.example.newsfeed.user.entity.User;

import java.time.LocalDateTime;

public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "commentId", nullable = false)
    private Comment comment;

    private LocalDateTime createAt;
}
