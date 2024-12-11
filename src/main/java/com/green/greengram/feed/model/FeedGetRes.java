package com.green.greengram.feed.model;

import com.green.greengram.feed.comment.model.FeedCommentGetRes;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
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
    private FeedCommentGetRes comment;


}
