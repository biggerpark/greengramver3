package com.green.greengram.feed;

import com.green.greengram.common.MyFileUtils;
import com.green.greengram.feed.comment.FeedCommentMapper;
import com.green.greengram.feed.comment.model.FeedCommentDto;
import com.green.greengram.feed.comment.model.FeedCommentGetReq;
import com.green.greengram.feed.comment.model.FeedCommentGetRes;
import com.green.greengram.feed.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final MyFileUtils myFileUtils;


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
            FeedCommentGetReq commentGetReq=new FeedCommentGetReq(feedGetRes.getFeedId(),0,3); // xml 문에 적용시킬, startIdx,size,feedId 를 FeedCommentGetReq 객체에 넣어줌.


            List<FeedCommentDto> commentList=feedCommentMapper.selFeedCommentListByFeedId(commentGetReq);

            FeedCommentGetRes commentGetRes=new FeedCommentGetRes();
            commentGetRes.setCommentList(commentList);
            commentGetRes.setMoreComment(commentList.size()==commentGetReq.getSize()); // 4개면 true, 4개 아니면 false

            if(commentGetRes.isMoreComment()){
                commentList.remove(commentList.size()-1); // 댓글 4개가 되면 마지막 댓글 삭제하고, 나중에 isMore 처리해주려고 이렇게 한것.
            }


            feedGetRes.setComment(commentGetRes); // 댓글 리스트를 각 피드에 할당해줌, 더보기 눌렀을때 나머지 댓글 보여주게 하는건 FeedCommentService 에서 할 것이다.

        }



        return list; //FeedGetRes 로 이루어진 리스트를 리턴해줌.
    }

    //select 2번
    public List<FeedGetRes> getFeedList2(FeedGetReq p){



       return null;


    }

    //select 3번
    public List<FeedGetRes> getFeedList3(FeedGetReq p) {
        //피드 리스트
        log.info("FeedGetReq : {}",p);
        List<FeedGetRes> list = feedMapper.selFeedList(p);
        log.info("FeedGetResList : {} ", list);
        //feed_id 를 골라내야한다.
//        List<Long> feedIds=list.stream().map(FeedGetRes::getFeedId).toList(); 밑의 3줄 코드와 같다.

        List<Long> feedIds = new ArrayList<>(); // feedId 만 골라내서 따로 리스트를 만들어줄것이다.

        for (FeedGetRes feedGetRes : list) {
            feedIds.add(feedGetRes.getFeedId());
        }

        log.info("feedIds:{}", feedIds);


        //피드와 관련된 사진 리스트
        List<FeedPicSel> feedPicList = feedPicsMapper.selFeedPicListByFeedIds(feedIds);
        log.info("FeedPicList:{}", feedPicList);


        //피드와 피드와관련된 사진리스트를 매핑시키자
        Map<Long,List<String>> picHashMap=new HashMap<>(); // key 인 Long 타입에 value 인 List<String> 타입을 넣어줌

        for(FeedPicSel item:feedPicList){
            long feedId=item.getFeedId(); // 피드와 관련된 사진 리스트의 feedId 값 가져옴
            if(!picHashMap.containsKey(feedId)){ // feedId 가 있는지 체크
                picHashMap.put(feedId,new ArrayList<String>());
            }
            List<String> pics=picHashMap.get(feedId); // feedId 에 해당하는 ArrayList, 즉 List<String> pics=new ArrayList<>() 이렇게 됨.
            pics.add(item.getPic());
        }

        for(FeedGetRes res:list){
            res.setPics(picHashMap.get(res.getFeedId()));
        }





//        int lastIndex=0;
//        for(FeedGetRes res:list){
//            List<String> pics=new ArrayList<>();
//            for(int i=lastIndex;i<feedPicList.size();i++){
//                FeedPicSel feedPicSel=feedPicList.get(i);
//                if(res.getFeedId()==feedPicSel.getFeedId()){
//                    pics.add(feedPicSel.getPic());
//                }else{
//                    res.setPics(pics);
//                    lastIndex=i;
//                    break;
//                }
//            }
//        }


        log.info("list:{}",list);


        //피드와 관련된 댓글 리스트

        List<FeedCommentDto> feedCommentDtoList = feedCommentMapper.selFeedCommentListByFeedIds(feedIds);


        Map<Long, FeedCommentGetRes> commentMap = new HashMap<>();
        for(FeedCommentDto dto : feedCommentDtoList){
            long feedId = dto.getFeedId();
            if(!commentMap.containsKey(feedId)){
                FeedCommentGetRes feedCommentGetRes = new FeedCommentGetRes();
                feedCommentGetRes.setCommentList(new ArrayList<>());
                commentMap.put(feedId, feedCommentGetRes);
            }
            FeedCommentGetRes feedCommentGetRes = commentMap.get(feedId);
            feedCommentGetRes.getCommentList().add(dto);
        }

        for(FeedGetRes r : list){
            r.setPics(picHashMap.get(r.getFeedId()));
            FeedCommentGetRes feedCommentGetRes = commentMap.get(r.getFeedId());

            if(feedCommentGetRes == null){
                feedCommentGetRes = new FeedCommentGetRes();
                feedCommentGetRes.setCommentList(new ArrayList<>());
            } else if(feedCommentGetRes.getCommentList().size()==4){
                feedCommentGetRes.setMoreComment(true);
                feedCommentGetRes.getCommentList().remove(feedCommentGetRes.getCommentList().size()-1);
            }
            r.setComment(feedCommentGetRes);
        }








        return list;

    }




    @Transactional
    public int deleteFeed(FeedDeleteReq p){
        //피드 댓글,좋아요,사진 삭제
        int affectedRows=feedMapper.delFeedLikeAndFeedCommentAndFeedPic(p);

        //피드 삭제
        int affectedRowsFeed=feedMapper.delFeed(p);

        String deletePath=String.format("%s/feed/%d",myFileUtils.getUploadPath(),p.getFeedId());
        myFileUtils.deleteFolder(deletePath,true);

        return 1;

    }

}
