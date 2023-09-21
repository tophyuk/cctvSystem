package com.kiot.cctvSystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchDto {
    private int pageNum = 1;
    private int pageSize = 2;
    private String searchType;
    private String searchKeyword;

    public SearchDto(int pageNum, int pageSize, String searchType, String searchKeyword) {
        this.pageNum = pageNum != 0 ? pageNum : this.pageNum; // 값이 들어올 경우 대체
        this.pageSize = pageSize != 0 ? pageSize : this.pageSize; // 값이 들어올 경우 대체
        this.searchType = searchType;
        this.searchKeyword = searchKeyword;
    }
}
