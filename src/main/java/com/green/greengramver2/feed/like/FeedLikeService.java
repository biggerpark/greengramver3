package com.green.greengramver2.feed.like;

import com.green.greengramver2.feed.FeedMapper;
import com.green.greengramver2.feed.like.model.FeedLikeReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedLikeService {
    private final FeedLikeMapper mapper;


    public int feedLikeToggle(FeedLikeReq p){
        int result=mapper.delFeedLike(p); //만약 넘어오는 값이 없으면 삭제할 투플이 없는것. 즉 좋아요가 안 누른 것이므로 0으로 넘어오므로, 밑의 if절을 만난다.

        if(result==0){
            return mapper.insFeedLike(p); // 좋아요 등록을 시켜, return 을 1로 해준다.
        }



        return 0;
    }
}
