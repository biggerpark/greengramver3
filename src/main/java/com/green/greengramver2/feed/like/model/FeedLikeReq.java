package com.green.greengramver2.feed.like.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedLikeReq {
    private long feedId;
    private long userId;
}
