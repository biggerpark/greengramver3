package com.green.greengramver2.feed.comment;

import com.green.greengramver2.common.model.ResultResponse;
import com.green.greengramver2.feed.comment.model.FeedCommentPostReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("feed/comment")
@RequiredArgsConstructor
public class FeedCommentController {
    private final FeedCommentService service;


    @PostMapping
    public ResultResponse<Long> insFeedComment(@RequestBody FeedCommentPostReq p){

        Long result = service.insFeedComment(p);


        return ResultResponse.<Long>builder().resultMsg("댓글등록완료").resultData(result).build();

    }


}
