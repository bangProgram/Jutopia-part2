package com.jbproject.jutopia.rest.dto.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.ser.std.NumberSerializers;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.jbproject.jutopia.common.CommonUtils;
import com.jbproject.jutopia.rest.entity.NyCorpDetailEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NyStockModel {



}
