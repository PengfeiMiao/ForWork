package com.mpf.website.service.tag.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.mpf.website.entity.tag.Tag;
import com.mpf.website.mapper.TagMapper;
import com.mpf.website.service.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author: MiaoPengfei
 * @date: 2021/3/7 17:06
 * @description:
 * @since: 1.0.20
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagMapper tagMapper;

    @Override
    public int deleteTags(String[] tags) {
        if (tags.length > 0) {
            return tagMapper.deleteByContent(tags);
        }
        return 0;
    }

    @Override
    public int updateTags(String[] tags, boolean flag) {
        int incr = flag ? 1 : -1;
        int count = 0;
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        for (String tag : tags) {
            wrapper.eq("content", tag);
            List<Tag> list = tagMapper.selectList(wrapper);
            if (!list.isEmpty()) {
                Tag first = list.get(0);
                if(first.getCount() > 0) {
                    first.setCount(first.getCount() + incr);
                    first.setUpdateTime(new Date());
                    count += tagMapper.updateById(first);
                }
            }else{
                Tag newTag = new Tag();
                newTag.setContent(tag);
                newTag.setCount(0);
                count += tagMapper.insert(newTag);
            }
        }
        return count;
    }
}
