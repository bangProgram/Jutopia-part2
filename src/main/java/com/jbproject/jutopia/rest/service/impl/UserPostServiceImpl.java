package com.jbproject.jutopia.rest.service.impl;

import com.jbproject.jutopia.config.security.jwt.AccessJwtToken;
import com.jbproject.jutopia.constant.ServerErrorCode;
import com.jbproject.jutopia.exception.ExceptionProvider;
import com.jbproject.jutopia.rest.entity.PostEntity;
import com.jbproject.jutopia.rest.entity.PostReplyRelation;
import com.jbproject.jutopia.rest.model.payload.PostSearchPayload;
import com.jbproject.jutopia.rest.model.payload.PostViewPayload;
import com.jbproject.jutopia.rest.model.result.PostResult;
import com.jbproject.jutopia.rest.model.result.ReplyResult;
import com.jbproject.jutopia.rest.repository.PostRepository;
import com.jbproject.jutopia.rest.service.UserPostService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserPostServiceImpl implements UserPostService {

    private final PostRepository postRepository;

    public List<PostResult> searchPostList(PostSearchPayload payload){
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
        List<ReplyResult> replyResults = postReplyRelationList.stream().map(PostReplyRelation::getReplyEntity).map(ReplyResult::create).toList();

        result.setReplyResults(replyResults);

        return result;
    }

    public Long savePost(PostViewPayload payload, AccessJwtToken.AccessJwtPrincipal principal){
        if(payload.getPostId() != null){
            payload.setPostWriterId(principal.getUserId());
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
}