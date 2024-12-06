package com.green.greengram.common;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

//swagger 처음 화면을 수정하고 싶으면 따로 class를 만들어서 이런식으로 적어주면된다.
@OpenAPIDefinition( // swagger 화면의 이름을 수정하는 법
        info = @Info(
                title="GreenGram",
                description = "그린그램 SNS",
                version = "v2"
        )
)
public class SwaggerConfiguration {

}
