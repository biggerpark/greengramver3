package com.green.greengramver2.feed.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FeedGetRes {
    private long feedId;
    private long writerUserId;
    private String contents;
    private String location;
    private String createdAt;
    private String writerNm;
    private String writerPic;
    private int isLike;

    // pics 가 여러개이므로 1대n 관계라는 것을 알 수 있다.
    private List<String> pics=new ArrayList<>();

}
