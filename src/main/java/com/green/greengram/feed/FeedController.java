package com.green.greengram.feed;

import com.green.greengram.common.model.ResultResponse;
import com.green.greengram.feed.model.*;
import io.swagger.v3.oas.annotations.Operation;
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


    @DeleteMapping
    @Operation(summary = "Feed 삭제", description = "피드의 댓글, 좋아요 모두 삭제 처리")
    public ResultResponse<Integer> deleteFeed(@ParameterObject @ModelAttribute FeedDeleteReq p) {
        log.info("FeedController > deleteFeed > p: {}", p);
        int result = service.deleteFeed(p);
        return ResultResponse.<Integer>builder()
                .resultMsg("피드가 삭제되었습니다.")
                .resultData(result)
                .build();
    }



    @GetMapping("list")
    public ResultResponse<List<FeedGetRes>> selFeedList3(@ParameterObject @ModelAttribute FeedGetReq p){

        log.info("FeedController>getFeedList>p:{}",p);
        List<FeedGetRes> list=new ArrayList<>();

        list=service.getFeedList3(p);


        return ResultResponse.<List<FeedGetRes>>builder().resultMsg(String.format("%d rows",list.size())).resultData(list).build();

    }

}
