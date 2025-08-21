package org.example.newsfeed.feed.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class FeedSaveRequestDto {

    @NotBlank(message = "제목은 필수 입력값입니다.")
    @Size(max=10, message="제목은 1글자 이상이여야 합니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력값입니다.")
    private String content;
}

