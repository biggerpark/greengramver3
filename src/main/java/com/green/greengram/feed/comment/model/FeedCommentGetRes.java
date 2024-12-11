package com.green.greengram.feed.comment.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FeedCommentGetRes {
  @Schema(title = "더 많은 댓글")
  private boolean moreComment;
  @Schema(title = "댓글")
  private List<FeedCommentDto> commentList;
}


