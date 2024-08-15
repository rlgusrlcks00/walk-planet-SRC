package com.cero.cm.common.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Schema(description = "검색 요청 공통 Req DTO")
public class PagingReq implements Serializable {

    @Schema(description = "페이징 적용 여부.", example = "true", defaultValue = "true")
    private Boolean isPaging = true;
}
