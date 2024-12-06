package com.green.greengram.feed.comment;

import com.green.greengram.common.model.ResultResponse;
import com.green.greengram.feed.comment.model.FeedCommentDelReq;
import com.green.greengram.feed.comment.model.FeedCommentGetReq;
import com.green.greengram.feed.comment.model.FeedCommentGetRes;
import com.green.greengram.feed.comment.model.FeedCommentPostReq;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResultResponse<FeedCommentGetRes> getFeedComment(@Parameter(description = "피드 PK", example = "12") @RequestParam("feed_id") long feedId
            , @Parameter(description = "튜플 시작 index", example = "3") @RequestParam("start_idx") int startIdx // 프론트에서 start_idx 값을 바로 보내줌.
            , @Parameter(description = "페이지 당 아이템 수", example = "20") @RequestParam(required = false, defaultValue = "20") int size){


        FeedCommentGetReq p = new FeedCommentGetReq(feedId, startIdx, size);
        log.info("FeedCommentController > getFeedComment > p: {}", p);
        FeedCommentGetRes res = service.getFeedComment(p);
        return ResultResponse.<FeedCommentGetRes>builder()
                .resultMsg(String.format("%d rows", res.getCommentList().size()))
                .resultData(res)
                .build();

    }

    @GetMapping("/request_param") // swagger 에서 볼려고 따로 만든것. 쿼리스트링으로 날아온 데이터가 자동으로 GetReq 의 멤버필드와 바인딩 된다. 따로 생성자를 안써주는 이유이다.
    public ResultResponse<FeedCommentGetRes> getFeedComment(@ParameterObject @ModelAttribute FeedCommentGetReq p){


        FeedCommentGetRes result = service.getFeedComment(p);


        return ResultResponse.<FeedCommentGetRes>builder().resultMsg("댓글 출력 완료").resultData(result).build();

    }


    // 삭제시 받아야 할 데이터 - feedCommentId+로그인한 사용자의 pk(feed_comment_id,signed_user_id)
    // FE-data 전달방식(쿼리스트링)
    @DeleteMapping
    public ResultResponse<Integer> delFeedComment(@ParameterObject @ModelAttribute FeedCommentDelReq p){
        log.info("p:{}",p);
        Integer result = service.delFeedComment(p);

        return ResultResponse.<Integer>builder().resultMsg("삭제완료").resultData(result).build();
    }

    @DeleteMapping("/request_param")
    public ResultResponse<Integer> delFeedComment(@RequestParam("feed_comment_id") long feedCommentId,@RequestParam("signed_user_id") long userId){
        FeedCommentDelReq p=new FeedCommentDelReq(feedCommentId,userId);

        Integer result = service.delFeedComment(p);
        return ResultResponse.<Integer>builder().resultMsg("삭제완료").resultData(result).build();
    }

}
