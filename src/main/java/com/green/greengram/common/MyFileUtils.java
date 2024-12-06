package com.green.greengram.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class MyFileUtils {
    private final String uploadPath;


    public MyFileUtils(@Value("${file.directory}") String uploadPath) { // Value 는 Springframework 를 호출해줘야 한다.
        this.uploadPath = uploadPath;
    }

    //해당경로에 파일 생성
    public String makeFolders(String path){
        File file = new File(uploadPath,path);
        file.mkdirs();
        return file.getAbsolutePath(); // 절대 경로 리턴
    }

    // 파일의 확장자 추출
    public String getExt(String file){
        int lastIdx=file.lastIndexOf(".");
        return file.substring(lastIdx);
    }

    //랜덤 파일명 생성
    public String randomFilName(){
        return UUID.randomUUID().toString();
    }

    //랜덤 파일명+확장자 만들기
    public String randomExt(String file){
        return randomFilName()+getExt(file);
    }

    //파일을 원하는 path 경로에 저장하기
    public void transferTo(MultipartFile file, String path) throws IOException {
        File dest = new File(uploadPath,path);
        file.transferTo(dest);
    }

    // MultiPartFile 타입으로 받은 파일을 랜덤한 이름과 확장자를 추출하기
    public String randomEExt(MultipartFile file){
        return randomExt(file.getOriginalFilename());
    }
}

