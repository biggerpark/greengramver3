package com.green.greengram.feed.like.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedLikeReq {
    private long feedId;
    private long userId;
}
