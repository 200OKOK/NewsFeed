package org.example.newsfeed.feed.dto;

import lombok.Getter;

@Getter
public class PageResponseDto<T> {
    private Integer page;
    private Integer size;
    private Integer totlaPages;
    private Long totalElements;
    private T posts;

    private PageResponseDto(Integer page,
                            Integer size,
                            Integer totalPages,
                            Long totalElements,
                            T posts){
        this.page = page;
        this.size = size;
        this.totlaPages = totalPages;
        this.posts = posts;
        this.totalElements = totalElements;
    }

    public static <T> PageResponseDto of(Integer page,
                                         Integer size,
                                         Integer totalPages,
                                         Long totalElements,
                                         T posts){ 
        return new PageResponseDto(page, size, totalPages, totalElements, posts);
    }

}
