package com.green.greengramver2.feed;

import com.green.greengramver2.common.MyFileUtils;
import com.green.greengramver2.feed.comment.FeedCommentMapper;
import com.green.greengramver2.feed.comment.model.FeedCommentDto;
import com.green.greengramver2.feed.comment.model.FeedCommentGetReq;
import com.green.greengramver2.feed.comment.model.FeedCommentGetRes;
import com.green.greengramver2.feed.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional // 데이터베이스 작업을 묶어서 처리하며,
// 작업 도중 오류가 발생하면 변경사항을 자동으로 롤백(취소)하여 데이터의 무결성을 유지합니다.
public class FeedService {
    private final MyFileUtils fileUtils;
    private final FeedMapper feedMapper;
    private final FeedPicsMapper feedPicsMapper;
    private final FeedCommentMapper feedCommentMapper;


    public FeedPostRes postFeed(List<MultipartFile> pics, FeedPostReq p){

        int result=feedMapper.postFeed(p);


        // 파일 등록
        long feedId=p.getFeedId();

        // 저장 폴더 만들기, 저장위치/feed/${feedId} 위치에 파일을 저장할 것이다.

        String middlePath=String.format("feed/%d", feedId);
        fileUtils.makeFolders(middlePath);


        //랜덤 파일명 저장용 >> feed_pics 테이블에 저장할 때 사용
        List<String> picNameList=new ArrayList<>(pics.size());


        for(MultipartFile pic:pics){


            String savedPicName=fileUtils.randomEExt(pic);
            picNameList.add(savedPicName); // 랜덤 파일명 담기.
            String filePath=String.format("%s/%s",middlePath,savedPicName);

            try{
                fileUtils.transferTo(pic,filePath);
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        //FeedPicDto 멤버필드 세팅하기
        FeedPicDto feedPicDto=new FeedPicDto();
        feedPicDto.setPics(picNameList);
        feedPicDto.setFeedId(feedId);

        int resultPics= feedPicsMapper.insFeedPics(feedPicDto);

        //FeedPostRes 에 Builder 에노테이션을 달아주면, setter 를 따로 안해줘도 바로 원하는 데이터 값을 리턴시켜줄 수 있음


//        FeedPostRes feedPostRes=new FeedPostRes();
//        feedPostRes.setPics(picNameList);
//        feedPostRes.setFeedId(feedId);
//        return feedPostRes

//         위에처럼 setter 를 통해 값을 넣고 리턴시켜주나, 밑에처럼 Builder 를 통해 리턴시켜주나 똑같음.

        return FeedPostRes.builder().feedId(feedId).pics(picNameList).build();


    }


    public List<FeedGetRes> getFeedList(FeedGetReq p){
        //N+1 이슈 발생
        List<FeedGetRes> list=feedMapper.selFeedList(p);



        for(FeedGetRes feedGetRes:list){

            feedGetRes.setPics(feedPicsMapper.selFeedPicList(feedGetRes.getFeedId())); //FeedGetRes 멤버필드인 List<String> pics 에 데이터를 넣어줌.


            //피드 당 댓글 4개

            FeedCommentGetReq commentGetReq=new FeedCommentGetReq();
            commentGetReq.setPage(1); //피드 리스트 가져올때, 피드당 3개의 댓글
            commentGetReq.setFeedId(feedGetRes.getFeedId());

            List<FeedCommentDto> commentList=feedCommentMapper.selFeedCommentListByFeedId(commentGetReq);

            FeedCommentGetRes commentGetRes=new FeedCommentGetRes();
            commentGetRes.setCommentList(commentList);
            commentGetRes.setMoreComment(commentList.size()==4); // 4개면 true, 4개 아니면 false

            if(commentGetRes.isMoreComment()){
                commentList.remove(commentList.size()-1); // 댓글 4개가 되면 마지막 댓글 삭제하고, 나중에 isMore 처리해주려고 이렇게 한것.
            }
            feedGetRes.setComment(commentGetRes); // 댓글 리스트를 각 피드에 할당해줌.

        }



        return list; //FeedGetRes 로 이루어진 리스트를 리턴해줌.
    }

}
