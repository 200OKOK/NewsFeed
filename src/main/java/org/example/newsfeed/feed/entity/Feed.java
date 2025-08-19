package org.example.newsfeed.feed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.newsfeed.common.BaseEntity;
import org.example.newsfeed.user.entity.User;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "feeds")
public class Feed extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;
    private String content;

    public Feed(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }
    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

}

