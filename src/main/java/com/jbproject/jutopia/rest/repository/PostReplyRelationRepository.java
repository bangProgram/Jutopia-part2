package com.jbproject.jutopia.rest.repository;

import com.jbproject.jutopia.rest.entity.PostReplyRelation;
import com.jbproject.jutopia.rest.entity.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReplyRelationRepository extends JpaRepository<PostReplyRelation, Long> {
}
