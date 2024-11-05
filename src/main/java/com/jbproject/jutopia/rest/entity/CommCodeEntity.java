package com.jbproject.jutopia.rest.entity;

import com.jbproject.jutopia.rest.entity.key.CommCodeKey;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="tb_comm_code")
@NoArgsConstructor
@IdClass(CommCodeKey.class)
public class CommCodeEntity extends BaseEntity {

    @Id
    @Column(name = "code")
    private String code;
    @Id
    @Column(name = "group_code")
    private String groupCode;

    @Column(name = "code_name")
    private String codeName;
    @Column(name = "seq")
    private int seq;
    @Column(name = "use_yn")
    private String useYn;
}
