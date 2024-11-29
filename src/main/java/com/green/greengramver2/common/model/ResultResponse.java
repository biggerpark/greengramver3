package com.green.greengramver2.common.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class ResultResponse<T> {
    @Schema(title="결과메세지")
    private String resultMsg;
    @Schema(title="결과")
    private T resultData;


}
