package com.green.greengram.user.follow;

import com.green.greengram.common.model.ResultResponse;
import com.green.greengram.user.follow.model.UserFollowReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("user/follow")
@RestController
public class UserFollowController {
    private final UserFollowService service;


    //팔로우 신청
    //RequestBody, 요청을 보내는 자가 body 에 json 형태의 데이터를 담아서 보낸다는 형식
    @PostMapping
    public ResultResponse<Integer> postUserFollow(@RequestBody UserFollowReq p) {
        log.info("postUserFollow {}", p);


        Integer result = service.postUserFollow(p);

        return ResultResponse.<Integer>builder().resultMsg("팔로우 신청 완료").resultData(result).build();

    }



    //팔로우 취소
    //쿼리스트링
    @DeleteMapping
    public ResultResponse<Integer> deleteUserFollow(@ParameterObject @ModelAttribute UserFollowReq p) {
        log.info("deleteUserFollow {}", p);

        Integer result=service.delUserFollow(p);

        return ResultResponse.<Integer>builder().resultMsg("팔로우 취소 완료").resultData(result).build();

    }

}
