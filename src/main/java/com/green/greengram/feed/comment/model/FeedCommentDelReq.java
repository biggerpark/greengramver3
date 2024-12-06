package com.green.greengram.feed.comment.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.BindParam;

@Getter
@Setter
@ToString
public class FeedCommentDelReq {
    @Schema(name="feed_comment_id")
    private long feedCommentId;
    @Schema(name="signed_user_id")
    private long userId;


//    @ConstructorProperties({"feed_comment_id","signed_user_id"}) // 이 에노테이션을 사용하면, ModelAttribute 로 받은 객체의 멤버필드를 바로 프론트랑 맞추어 줄 수 있다.
    public FeedCommentDelReq(@BindParam("feed_comment_id") long feedCommentId,@BindParam("signed_user_id") long userId) { // 이렇게 생성자 매개변수에 각각 BindParam 을 적어줘도 되고, 위에 ConstructorProperties 를 적어서 해줘도 된다.
        this.feedCommentId = feedCommentId;
        this.userId = userId;
    }



}
