package com.jbproject.jutopia.rest.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Getter
@Setter
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class CommCodeKey implements Serializable {

    private String code;
    private String groupCode;
}
