package com.green.greengram.feed.comment;

import com.green.greengram.feed.comment.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor

public class FeedCommentService {
    private final FeedCommentMapper mapper;


    public Long insFeedComment(FeedCommentPostReq p){

        int result=mapper.insFeedComment(p);

        Long feedCommentId = p.getFeedCommentId();

        return feedCommentId;

    }

    public FeedCommentGetRes getFeedComment(FeedCommentGetReq p){
        FeedCommentGetRes res = new FeedCommentGetRes();
        if(p.getStartIdx() < 0) {
            res.setCommentList(new ArrayList<>());
            return res;
        }
        List<FeedCommentDto> commentList = mapper.selFeedCommentListByFeedId(p); //startIdx,size 값을 가진 GetReq 의 객체를 넣어서, 댓글을 commentList 에 넣어줌.
        res.setCommentList(commentList);
        res.setMoreComment( commentList.size() == p.getSize() ); //사이즈가 21이면 true,아니며 false- 이건 댓글이 더 있는지 알려주기 위한 boolean
        if(res.isMoreComment()) { //true 면 리스트의 마지막을 삭제해줌.-삭제된 부분은 isMore 처리를 해주는 것.
            commentList.remove(commentList.size() - 1);
        }
        return res;
    }




    public Integer delFeedComment(FeedCommentDelReq p){
        Integer result=mapper.delFeedComment(p);



        return result;
    }
}
