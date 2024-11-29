package com.green.greengramver2.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpReq {
    @Schema(title = "아이디",example = "yaho",requiredMode = Schema.RequiredMode.REQUIRED)
    private String uid;
    @Schema(title = "닉네임",example = "홍길동")
    private String nickName;
    @Schema(title = "비밀번호",example = "1111",requiredMode = Schema.RequiredMode.REQUIRED)
    private String upw;


    @JsonIgnore
    private String pic;
    @JsonIgnore
    private long userId;

}
