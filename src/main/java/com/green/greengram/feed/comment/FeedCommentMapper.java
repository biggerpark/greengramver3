package com.green.greengram.feed.comment;

import com.green.greengram.feed.comment.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedCommentMapper {
    int insFeedComment(FeedCommentPostReq p);
//    FeedCommentGetRes selFeedComment(FeedCommentGetReq p);
    List<FeedCommentDto> selFeedCommentListByFeedId(FeedCommentGetReq p);
    int delFeedComment(FeedCommentDelReq p);
    List<FeedCommentDto> selFeedCommentListByFeedIds(List<Long> feedIds);
}
