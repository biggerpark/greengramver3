package com.green.greengram.user;

import com.green.greengram.common.model.ResultResponse;
import com.green.greengram.user.model.SignInReq;
import com.green.greengram.user.model.SignInRes;
import com.green.greengram.user.model.SignUpReq;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
}
