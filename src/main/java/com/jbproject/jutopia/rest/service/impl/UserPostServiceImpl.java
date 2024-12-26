package com.jbproject.jutopia.rest.service.impl;

import com.jbproject.jutopia.config.security.jwt.AccessJwtToken;
import com.jbproject.jutopia.constant.ServerErrorCode;
import com.jbproject.jutopia.exception.ExceptionProvider;
import com.jbproject.jutopia.rest.entity.PostEntity;
import com.jbproject.jutopia.rest.entity.relation.PostReplyRelation;
import com.jbproject.jutopia.rest.entity.ReplyEntity;
import com.jbproject.jutopia.rest.model.payload.SearchPostPayload;
import com.jbproject.jutopia.rest.model.payload.ViewPostPayload;
import com.jbproject.jutopia.rest.model.payload.ReplyPayload;
import com.jbproject.jutopia.rest.model.payload.SearchReplyPayload;
import com.jbproject.jutopia.rest.model.result.PostResult;
import com.jbproject.jutopia.rest.model.result.ReplyResult;
import com.jbproject.jutopia.rest.repository.PostReplyRepository;
import com.jbproject.jutopia.rest.repository.PostRepository;
import com.jbproject.jutopia.rest.repository.ReplyRepository;
import com.jbproject.jutopia.rest.service.UserPostService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserPostServiceImpl implements UserPostService {

    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;
    private final PostReplyRepository postReplyRepository;

    public List<PostResult> searchPostList(SearchPostPayload payload){
        return postRepository.searchPostList(payload);
    }

    public PostResult getPostDetail(Long postId){
        PostEntity curPost = postRepository.findById(postId).orElseThrow(
                () -> new ExceptionProvider(ServerErrorCode.POST_404_01)
        );

        PostResult result = PostResult.builder()
                .postId(curPost.getId())
                .postType(curPost.getPostType())
                .postTitle(curPost.getPostTitle())
                .postDetail(curPost.getPostDetail())
                .stockCode(curPost.getStockCode())
                .stockName(curPost.getStockName())
                .postWriter(curPost.getPostWriter())
                .postViewrs(curPost.getPostViewrs())
                .build();

        List<PostReplyRelation> postReplyRelationList = curPost.getPostReplyRelation();

        List<ReplyResult> allReplyList = postReplyRelationList.stream().map(PostReplyRelation::getReplyEntity).toList().stream().map(ReplyResult::create).toList();

        List<ReplyResult> resultList = new ArrayList<>();
        Map<Long, ReplyResult> parent = new HashMap<>();

        for (ReplyResult replyResult : allReplyList) {
            parent.put(replyResult.getReplyId(), replyResult);

            if(replyResult.getParentId() == null) {
                resultList.add(replyResult);
            }else {
                parent.get(replyResult.getParentId())
                        .getChildReplyList()
                        .add(replyResult);
            }
        }

        result.setReplyResultList(resultList);

        System.out.println("replyResultList : "+resultList);

        return result;
    }

    public Long savePost(ViewPostPayload payload, AccessJwtToken.AccessJwtPrincipal principal){
        if(payload.getPostId() != null){
            payload.setPostWriterId(principal.getUserName());
            PostEntity curPost = postRepository.findById(payload.getPostId()).orElseThrow(
                    () -> new ExceptionProvider(ServerErrorCode.POST_404_01)
            );

            curPost.updatePost(payload);
            postRepository.save(curPost);
            return payload.getPostId();
        }else{
            PostEntity newPost = PostEntity.builder()
                    .postType(payload.getPostType())
                    .postTitle(payload.getPostTitle())
                    .postDetail(payload.getPostDetail())
                    .stockCode(payload.getStockCode())
                    .stockName(payload.getStockName())
                    .postWriter(principal.getUserName())
                    .createId(principal.getUserId())
                    .build();

            PostEntity curPost = postRepository.save(newPost);
            return curPost.getId();
        }
    }

    public List<ReplyResult> searchReplyList(SearchReplyPayload payload){
        List<PostReplyRelation> postReplyRelationList = postReplyRepository.findByPostId(payload.getPostId());


        List<ReplyResult> replyResultList =
                postReplyRelationList
                        .stream().map(PostReplyRelation::getReplyEntity).toList()
                        .stream().map(ReplyResult::create).toList();
        /*
        int maxDepth = Collections.max(replyResultList.stream().map(ReplyResult::getReplyDepth).distinct().toList());

        Map<Integer,List<ReplyResult>> replyGroup = replyResultList.stream().collect(Collectors.groupingBy(ReplyResult::getReplyDepth));

        // 댓글 작업 해야함

        ReplyResult result = replyResultList.getFirst();
//        for(int i=1; i<=maxDepth; i++){
//            result.setChildReplyList());
//        }*/
        return replyResultList;
    }

    public void savePostReply(ReplyPayload payload, AccessJwtToken.AccessJwtPrincipal principal){

        Long postId = payload.getPostId();
        Long replyId;

        if(payload.getReplyId() != null){

            payload.setReplyWriter(principal.getUserName());
            ReplyEntity curReply = replyRepository.findById(payload.getReplyId()).orElseThrow(
                    () -> new ExceptionProvider(ServerErrorCode.POST_404_01)
            );

            curReply.updateReply(payload);
            ReplyEntity reply = replyRepository.save(curReply);
            replyId = reply.getId();
        }else{

            ReplyEntity newReply = ReplyEntity.builder()
                    .replyDetail(payload.getReplyDetail())
                    .replyWriter(principal.getUserName())
                    .parentId(payload.getParentId())
                    .supperId(payload.getSupperId())
                    .replyDepth(payload.getReplyDepth())
                    .createId(principal.getUserId())
                    .updateId(principal.getUserId())
                    .build();

            ReplyEntity reply = replyRepository.save(newReply);
            replyId = reply.getId();
        }

        PostReplyRelation postReply = PostReplyRelation.builder()
                .postId(postId)
                .ReplyId(replyId)
                .build();

        postReplyRepository.save(postReply);
    }

}
