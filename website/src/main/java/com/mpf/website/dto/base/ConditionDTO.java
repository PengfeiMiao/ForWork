package com.mpf.website.dto.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: MiaoPengfei
 * @date: 2021/2/26 12:48
 * @description:
 * @since: 1.0.20
 */
@Data
public class ConditionDTO<T> {

    @ApiModelProperty("筛选条件")
    private List<Column> columns;

    @ApiModelProperty("查询页数")
    private Integer page;

    @ApiModelProperty("查询数量")
    private Integer pageSize;

    @ApiModelProperty("排序字段")
    private String sortCode;

    @ApiModelProperty("排序类型 升序:asc 降序:desc")
    private String sortType;

    public QueryWrapper<T> generateQueryWrapper() {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        return wrapper;
    }

    public Page<T> generatePageHelper() {
        return new Page<>(this.page, this.pageSize);
    }

}
