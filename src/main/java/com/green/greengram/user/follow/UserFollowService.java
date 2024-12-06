package com.green.greengram.user.follow;

import com.green.greengram.user.follow.model.UserFollowReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserFollowService {
    private final UserFollowMapper mapper;

    public int postUserFollow(UserFollowReq p){

        int result=mapper.postUserFollow(p);

        return result;
    }


    public int delUserFollow(UserFollowReq p){
        int result=mapper.delUserFollow(p);

        return result;
    }
}
