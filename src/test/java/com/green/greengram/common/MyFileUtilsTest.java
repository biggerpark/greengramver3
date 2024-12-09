package com.green.greengram.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyFileUtilsTest {

    private final String FILE_DIRECTORY ="D:/pjh/GreenGramVer3/greengramver3";
    MyFileUtils myFileUtils;

    @BeforeEach // 각각각 메소드 실행할때마다, 객체를 새롭게 생성하여 test 할 수 있게 해줄려면 setUp() 과 이 에노테이션을 달아준다
    void setUp() {
        myFileUtils=new MyFileUtils(FILE_DIRECTORY);
    }

    @Test
    void deleteFolder() {
        String path=String.format("%s/user/2",myFileUtils.getUploadPath());

        myFileUtils.deleteFolder(path,false);
    }

}