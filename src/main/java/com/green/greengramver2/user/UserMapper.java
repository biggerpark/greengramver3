package com.green.greengramver2.user;

import com.green.greengramver2.user.model.SignInReq;
import com.green.greengramver2.user.model.SignInRes;
import com.green.greengramver2.user.model.SignUpReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insUser(SignUpReq P);
    SignInRes selUserByUid(String uid);

}
