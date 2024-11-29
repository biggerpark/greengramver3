package com.green.greengramver2.feed;

import com.green.greengramver2.common.model.ResultResponse;
import com.green.greengramver2.feed.model.FeedGetReq;
import com.green.greengramver2.feed.model.FeedGetRes;
import com.green.greengramver2.feed.model.FeedPostReq;
import com.green.greengramver2.feed.model.FeedPostRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("feed")
@RequiredArgsConstructor
@Slf4j
public class FeedController {
    private final FeedService service;

    @PostMapping
    public ResultResponse<FeedPostRes> postFeed(@RequestPart List<MultipartFile> pics, @RequestPart FeedPostReq p){
        FeedPostRes res = service.postFeed(pics,p);

        return ResultResponse.<FeedPostRes>builder().resultMsg("피드등록완료").resultData(res).build();
    }


    @GetMapping
    public ResultResponse<List<FeedGetRes>> selFeedList(@ParameterObject @ModelAttribute FeedGetReq p){

        log.info("FeedController>getFeedList>p:{}",p);
        List<FeedGetRes> list=new ArrayList<>();

        list=service.getFeedList(p);


        return ResultResponse.<List<FeedGetRes>>builder().resultMsg(String.format("%d rows",list.size())).resultData(list).build();

    }

}
