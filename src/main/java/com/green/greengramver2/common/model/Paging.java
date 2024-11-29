package com.green.greengramver2.common.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Paging {
    private final static Integer DEFAULT_PAGE_SIZE = 20;

    @Schema(title = "페이지",example = "1")
    private int page;
    @Schema(title = "사이즈",example = "10")
    private int size;
    @JsonIgnore
    private int startIdx;


    public Paging(Integer page, Integer size) {
        this.page = (page == null || page <= 0) ? 1 : page;
        this.size = (size == null || size <= 0) ? DEFAULT_PAGE_SIZE : size;
        this.startIdx = (this.page - 1) * this.size;
    }

}
