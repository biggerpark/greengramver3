package com.green.greengramver2.user;

import com.green.greengramver2.common.MyFileUtils;
import com.green.greengramver2.user.model.SignInReq;
import com.green.greengramver2.user.model.SignInRes;
import com.green.greengramver2.user.model.SignUpReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserMapper mapper;
    private final MyFileUtils fileUtils;

    public int insUser(MultipartFile pic,SignUpReq p){

        String savedPicName=pic!=null?fileUtils.randomEExt(pic):null;
        p.setPic(savedPicName);


        String hashPassword= BCrypt.hashpw(p.getUpw(), BCrypt.gensalt());
        p.setUpw(hashPassword);

        int result=mapper.insUser(p);

        if(pic==null){
            return result;
        }

        long userId=p.getUserId();

        String middlePath=String.format("user/%d",userId);
        String filePath=String.format("%s/%s",middlePath,savedPicName);

        fileUtils.makeFolders(middlePath);
        try{
            fileUtils.transferTo(pic,filePath);
        }catch (IOException e){
            e.getMessage();
        }


        return result;
    }

    public SignInRes selUserByUid(SignInReq p){

        String uid=p.getUid();

        SignInRes res=mapper.selUserByUid(uid);

        if(res==null){
            res=new SignInRes();
            res.setMessage("아이디를 확인해주세요");
            return res;
        }else if(!BCrypt.checkpw(p.getUpw(),res.getUpw())){
            res=new SignInRes();
            res.setMessage("비밀번호를 확인해주세요");
            return res;
        }

        res.setMessage("로그인 성공");

        return res;
    }


}
