package com.green.greengram.common.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@Builder
@ToString
public class ResultResponse<T> {
    @Schema(title="결과메세지")
    private String resultMsg;
    @Schema(title="결과")
    private T resultData;


}
