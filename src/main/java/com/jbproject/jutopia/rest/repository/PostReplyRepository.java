package com.jbproject.jutopia.rest.repository;

import com.jbproject.jutopia.rest.entity.PostReplyRelation;
import com.jbproject.jutopia.rest.entity.ReplyEntity;
import com.jbproject.jutopia.rest.repository.custom.ReplyCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostReplyRepository extends JpaRepository<PostReplyRelation, Long> {
    List<PostReplyRelation> findByPostId(Long postId);
}
