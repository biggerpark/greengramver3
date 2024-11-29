package com.green.greengramver2.feed.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
public class FeedPostRes {
    private long feedId;
    private List<String> pics;

}
