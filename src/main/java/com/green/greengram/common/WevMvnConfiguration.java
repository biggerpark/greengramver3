package com.green.greengram.common;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@Component
public class WevMvnConfiguration implements WebMvcConfigurer {
    private final String uploadPath;

    public WevMvnConfiguration(@Value("${file.directory}") String uploadPath) {
        this.uploadPath = uploadPath;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pic/**").addResourceLocations("file:" + uploadPath+"/");
    }

    //@Bean 에노테이션이 붙으면 return 타입이 있어야한다. 타입은 객체타입이어야 한다.
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        //RestController 의 모든 url에 "/api" prefix를 설정
        //원래는 우리가 yaml에 모든 경로에 자동으로 앞에 /api 가 붙도로 설정했는데, 그렇게 했더니 swagger 쪽에서도
        // api 가 붙었다. 그래서 controller 쪽 경로에만 /api 가 붙도록 해주고 싶어서 이런 코드를 작성했따
        configurer.addPathPrefix("api", HandlerTypePredicate.forAnnotation(RestController.class));
        // RestController.class 가 의미하는건, RestController 가 붙은 모든 클래스를 의미
    }
}
