package com.green.greengram.user;

import com.green.greengram.common.MyFileUtils;
import com.green.greengram.user.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserMapper mapper;
    private final MyFileUtils fileUtils;
    private final MyFileUtils myFileUtils;

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

    public UserInfoGetRes getUserInfo(UserInfoGetReq p){
        return mapper.selUserInfo(p);
    }

    public String patchUserPic(UserPicPatchReq p){
        // 1. 저장할 파일명 생성(랜덤한 파일명) 생성
        MultipartFile pic=p.getPic();
        String savedPicName=(p.getPic()!=null?fileUtils.randomEExt(pic):null);


        //폴더만들기(최초 프로필 사진이 없었따면 폴더가 없기 때문)
        String folderPath=String.format("user/%d",p.getSignedUserId());
        myFileUtils.makeFolders(folderPath);

        // 2. 기존 파일 삭제 (DELETE) (방법 2가지, 1)폴더를 지운다, 2) select 해서 기존 파일명을 얻어온다, 3) 기존 파일명을 FE 에서 받는다)
        String deletePath=String.format("%s/user/%d",myFileUtils.getUploadPath(),p.getSignedUserId());
        myFileUtils.deleteFolder(deletePath,false);


        // 3. DB 에 투플을 수정(UPDATE) 한다


        p.setPicName(savedPicName);

        int result=mapper.updUserPic(p);

        if(p.getPic()==null){
            return null;
        }


        // 4. 원하는 위치에 저장할 파일명으로 파일을 이동(transferTo) 한다.
        String filePath=String.format("user/%d/%s",p.getSignedUserId(),savedPicName);

        try {
            myFileUtils.transferTo(p.getPic(),filePath);
        }catch (IOException e){
            e.printStackTrace();
        }





        return savedPicName;
    }


}
