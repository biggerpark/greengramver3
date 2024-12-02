package com.green.greengramver2.feed.comment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.greengramver2.common.model.Paging;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedCommentGetReq  {
    private final static int FIRST_COMMENT_SIZE=3;
    private final static int DEFAULT_PAGE_SIZE = 20;

    @Schema(title="피드 pk",example = "1",requiredMode = Schema.RequiredMode.REQUIRED)
    private long feedId;



    @JsonIgnore
    private int startIdx;

    @JsonIgnore
    private int size;

    public void setPage(int page){
        if(page<1) {
            return; // 조건을 만족하면 메소드 종료
        }
        if(page==1){
            startIdx=0;
            size=FIRST_COMMENT_SIZE+1; // +1은 isMore 처리용
            return; // 조건을 만족하면 메소드 종료

        }
        startIdx=((page-2)*DEFAULT_PAGE_SIZE)+FIRST_COMMENT_SIZE; // 페이지가 2로 넘어가면 이러한 로직 처리
        size=DEFAULT_PAGE_SIZE+1; // +1은 isMore 처리용
    }


}
