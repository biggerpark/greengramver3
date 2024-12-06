package com.green.greengram.feed.comment.model;

import com.green.greengram.common.model.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.beans.ConstructorProperties;

@Getter
@Setter
public class FeedCommentGetReq  {
//    private final static int FIRST_COMMENT_SIZE=3;
//    private final static int DEFAULT_PAGE_SIZE = 20;
//
//    @Schema(title="피드 pk",name="feed_id",example = "1",requiredMode = Schema.RequiredMode.REQUIRED)
//    private long feedId;
//    @Schema(title="페이지", example = "1",description = "2이상 값만 사용해주세요, 아이템수는 20개 입니다",requiredMode = Schema.RequiredMode.REQUIRED)
//    private int page;
//
//    @JsonIgnore
//    private int startIdx;
//
//    @JsonIgnore
//    private int size;
//
//    public FeedCommentGetReq(@BindParam("feed_id") long feedId,int page) {
//        this.feedId = feedId;
//        setPage(page);
//    }
//
//
//
//    public void setPage(int page){
//        this.page = page;
//        if(page<1) {
//            return; // 조건을 만족하면 메소드 종료
//        }
//        if(page==1){
//            startIdx=0;
//            size=FIRST_COMMENT_SIZE+1; // +1은 isMore 처리용
//            return; // 조건을 만족하면 메소드 종료
//
//        }
//
//
//        startIdx=((page-2)*DEFAULT_PAGE_SIZE)+FIRST_COMMENT_SIZE; // 페이지가 2로 넘어가면 이러한 로직 처리
//        size=DEFAULT_PAGE_SIZE+1; // +1은 isMore 처리용
//    }
//
//
    private final static int FIRST_COMMENT_SIZE = 3;

    @Schema(title="피드 PK", description = "피드 PK", name="feed_id", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private long feedId;

    @Schema(title="튜플 시작 index", description = "댓글 Element 갯수를 보내주면 된다.", name="start_idx", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
    private int startIdx;

    @Schema(title="페이지 당 아이템 수", description = "default: 20", example = "20")
    private int size;

    @ConstructorProperties({"feed_id", "start_idx", "size"})
    public FeedCommentGetReq(long feedId, int startIdx, Integer size) {
        this.feedId = feedId;
        this.startIdx = startIdx;
        this.size = (size == null ? Constants.getDefault_page_size() : size) + 1; // Constants 클래스의 static 메소드인 getDefault_page_size() 호출.
    }
}

