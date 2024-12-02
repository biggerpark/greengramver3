package com.green.greengramver2.feed.comment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title="피드 댓글 등록 요청")
public class FeedCommentPostReq {
    //쿼리스트링은 title 이 안먹힌다. 그때는 description 을 써줘야 한다.


    @Schema(title="피드 pk",example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private long feedId;
    @Schema(title="로그인한 유저 pk",example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private long userId;
    @Schema(title="댓글내용",example = "댓글입니다", requiredMode = Schema.RequiredMode.REQUIRED)
    private String comment;

    @JsonIgnore
    private Long feedCommentId;
}
