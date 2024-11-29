package com.green.greengramver2.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedPostReq {
    private long writerUserId;
    private String contents;
    private String location;

    @JsonIgnore
    private long feedId;
}
