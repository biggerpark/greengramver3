package com.green.greengram.user;

import com.green.greengram.user.model.SignInRes;
import com.green.greengram.user.model.SignUpReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insUser(SignUpReq P);
    SignInRes selUserByUid(String uid);

}
