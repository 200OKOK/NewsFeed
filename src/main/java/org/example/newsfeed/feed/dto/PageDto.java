package org.example.newsfeed.feed.dto;

public class PageDto {
    private int pageNum;
    private int pageSize;
    private int totalCount;
    private int totalPage;

    public PageDto(int pageNum, int pageSize, int totalCount, int totalPage) {
        this.pageNum = pageNum+1;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPage = totalPage;
    }
}
