package com.green.greengram.user;

import com.green.greengram.common.model.ResultResponse;
import com.green.greengram.user.model.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
@Slf4j
public class UserController {
    private final UserService service;

    @PostMapping("sign-up")
    @Operation(summary = "회원가입")
    public ResultResponse<Integer> insUser(@RequestPart(required = false) MultipartFile pic, @RequestPart SignUpReq p){

        int result= service.insUser(pic, p);



        return ResultResponse.<Integer>builder().resultMsg("회원가입완료").
                resultData(result)
                .build();
    }


    @PostMapping("sign-in")
    public ResultResponse<SignInRes> selUserByUid(@RequestBody SignInReq p){

        SignInRes res=service.selUserByUid(p);

        return ResultResponse.<SignInRes>builder().resultMsg(res.getMessage()).resultData(res).build();

    }

    @GetMapping
    public ResultResponse<UserInfoGetRes> getUserInfo(@ParameterObject@ModelAttribute UserInfoGetReq p){
        log.info("getUserInfo:{}",p);
        UserInfoGetRes result= service.getUserInfo(p);
        return ResultResponse.<UserInfoGetRes>builder().resultMsg("유저 프로필 정보").resultData(result).build();
    }

    @PatchMapping("pic") // PatchMapping 은 부분수정할때 사용,modelattribute 달아주면 formdata 로 받을 수 있다.
    public ResultResponse<String> patchProfilePic(@ModelAttribute UserPicPatchReq p){
        log.info("patchProfilePic:{}",p);
        String pic=service.patchUserPic(p);


        return ResultResponse.<String>builder().resultMsg("프로필 수정 완료").resultData(pic).build();
    }


}
