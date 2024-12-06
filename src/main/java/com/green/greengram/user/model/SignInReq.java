package com.green.greengram.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInReq {
    @Schema(title = "로그인",example = "mic",requiredMode = Schema.RequiredMode.REQUIRED)
    private String uid;
    @Schema(title = "비밀번호",example = "1212",requiredMode = Schema.RequiredMode.REQUIRED)
    private String upw;
}
