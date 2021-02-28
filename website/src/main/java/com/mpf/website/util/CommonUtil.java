package com.mpf.website.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mpf.website.dto.base.Column;
import com.mpf.website.dto.base.ConditionDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @author: MiaoPengfei
 * @date: 2021/2/28 18:00
 * @description:
 * @since: 1.0.20
 */
public class CommonUtil {
    public static <T> QueryWrapper<T> convertToWrapper(ConditionDTO<T> condition) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        for (Column column : condition.getColumns()) {
            Integer cond = column.getCond();
            String name =  column.getName();
            String value = column.getValue();
            if (cond != null && StringUtils.isNoneBlank(name) && StringUtils.isNoneBlank(value)) {
                switch (cond) {
                    case 0:
                        wrapper.eq(name, value);
                        break;
                    case 1:
                        wrapper.ne(name, value);
                        break;
                    case 2:
                        wrapper.gt(name, value);
                        break;
                    case 3:
                        wrapper.lt(name,value);
                        break;
                    case 4:
                        wrapper.like(name, value);
                        break;
                    case 5:
                        String[] arr = value.split(",");
                        wrapper.in(name, Arrays.asList(arr));
                        break;
                    case 6:
                        String[] twice = value.split(",");
                        wrapper.between(name, twice[0], twice[1]);
                        break;
                    default: break;
                }
            }
        }
        String sortCode = condition.getSortCode();
        boolean isAsc = StringUtils.isBlank(condition.getSortType()) || "asc".equals(condition.getSortType());
        if(StringUtils.isNoneBlank(sortCode)) {
            wrapper.orderBy(true, isAsc, sortCode);
        }
        return wrapper;

    }
}
