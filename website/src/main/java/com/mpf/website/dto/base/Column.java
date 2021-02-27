package com.mpf.website.dto.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: MiaoPengfei
 * @date: 2021/2/26 12:22
 * @description:
 * @since: 1.0.20
 */
@Data
@ApiModel
public class Column {

    @ApiModelProperty("列名")
    private String name;
    @ApiModelProperty("列值")
    private String value;
    @ApiModelProperty("过滤条件类型（0:=,1:like,2:<>,3:<,4:<=,5:>,6:>=,7:时间=,8:时间>=,9:时间<=,10:在列表,11:in）")
    private Integer cond;

}
