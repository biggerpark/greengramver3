package com.green.greengram.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "유저 정보 GET 응답")
public class UserInfoGetRes {
    private long userId;
    private String pic;
    private String createdAt;
    private String nickName;
    private int follower;
    private int following;
    private int feedCount;
    private int myFeedLikeCount;
    private int followState;

}
