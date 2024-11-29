package com.green.greengramver2.feed.model;

import com.green.greengramver2.common.model.Paging;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.BindParam;

@Slf4j
@Getter
@ToString(callSuper = true) // 자식 객체에 이 에노테이션을 달아주면, 부모에객체에도 ToString 이 적용된다.
public class FeedGetReq extends Paging {

    @Schema(title = "로그인 유저 PK",name="signed_user_id", requiredMode = Schema.RequiredMode.REQUIRED)
    private long signedUserId; //좋아요 기능 받기 위해 userId 데이터를 멤버필드에 추가


    // @ConstructorProperties({"page","size","signed_user_id"})
    //쿼리 스트링일때만, 언더바 형태의 키값으로 프론트에서 데이터를 받고 싶으면 BindParam 에노테이션을 달아주면 된다.
    public FeedGetReq(Integer page, Integer size, @BindParam("signed_user_id") long signedUserId) {
        super(page,size);
        log.info("FeedGetReq {}, {}, {}", page, size, signedUserId);
        this.signedUserId = signedUserId;
    }

}
