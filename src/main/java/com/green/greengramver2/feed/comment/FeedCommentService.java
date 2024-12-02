package com.green.greengramver2.feed.comment;

import com.green.greengramver2.feed.FeedMapper;
import com.green.greengramver2.feed.FeedService;
import com.green.greengramver2.feed.comment.model.FeedCommentPostReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor

public class FeedCommentService {
    private final FeedCommentMapper mapper;


    public Long insFeedComment(FeedCommentPostReq p){

        int result=mapper.insFeedComment(p);

        Long feedCommentId = p.getFeedCommentId();

        return feedCommentId;

    }
}
