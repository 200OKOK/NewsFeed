package org.example.newsfeed.feed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.newsfeed.user.entity.User;

@Getter
@Entity
@NoArgsConstructor

//@Table(name = "feeds")
public class Feed {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true)
    private Long feedId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    public Feed(String title, String content) {
        this.title = title;
        this.content = content;
    }

}