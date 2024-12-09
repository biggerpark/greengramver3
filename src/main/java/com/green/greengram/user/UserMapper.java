package com.green.greengram.user;

import com.green.greengram.user.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    int insUser(SignUpReq P);
    SignInRes selUserByUid(String uid);
    UserInfoGetRes selUserInfo(UserInfoGetReq p);
    int updUserPic(UserPicPatchReq p);

}
