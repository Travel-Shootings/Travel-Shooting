package com.sparta.travelshooting.admin.dto;

import lombok.Getter;

@Getter
public class AdminSummaryResponseDto {

    private Long allUsersCount = 0L;
    private Long allPostsCount = 0L;
    private Long allCommentsCount = 0L;

    public AdminSummaryResponseDto(Long allUsersCount, Long allPostsCount, Long allCommentsCount) {
        this.allUsersCount = allUsersCount;
        this.allPostsCount = allPostsCount;
        this.allCommentsCount = allCommentsCount;
    }
}
