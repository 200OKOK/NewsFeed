package org.example.newsfeed.like.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateFeedLikeResp {


    private long userId;
    private long feedId;
    private LocalDateTime createdAt;


}
