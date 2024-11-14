package com.jbproject.jutopia.rest.repository;

import com.jbproject.jutopia.rest.entity.PostEntity;
import com.jbproject.jutopia.rest.repository.custom.PostCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long>, PostCustom {
}
