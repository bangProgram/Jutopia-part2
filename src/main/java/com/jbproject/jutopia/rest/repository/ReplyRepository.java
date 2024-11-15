package com.jbproject.jutopia.rest.repository;

import com.jbproject.jutopia.rest.entity.PostEntity;
import com.jbproject.jutopia.rest.entity.ReplyEntity;
import com.jbproject.jutopia.rest.repository.custom.ReplyCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long>, ReplyCustom {
}
