package com.jbproject.jutopia.rest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @CreatedDate
    @Column(name = "create_dttm", updatable = false)
    private LocalDateTime createDttm;

    @Column(name = "create_id", updatable = false)
    private String createId;

    @LastModifiedDate
    @Column(name = "update_dttm")
    private LocalDateTime updateDttm;

    @Column(name = "update_id")
    private String updateId;
}
