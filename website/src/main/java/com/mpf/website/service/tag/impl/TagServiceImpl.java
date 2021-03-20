package com.mpf.website.service.tag.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mpf.website.entity.tag.Tag;
import com.mpf.website.mapper.TagMapper;
import com.mpf.website.service.tag.TagService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        // 注意tags数量很多的情况 for循环查询
        for (String tag : tags) {
            wrapper.clear();
            wrapper.eq("content", tag);
            wrapper.eq("flag", 0);
            List<Tag> list = tagMapper.selectList(wrapper);
            if (!list.isEmpty()) {
                Tag first = list.get(0);
                count += saveTag(first, incr);
            } else {
                Tag newTag = new Tag();
                newTag.setContent(tag);
                newTag.setCount(0);
                count += tagMapper.insert(newTag);
            }
        }
        return count;
    }

    @Override
    public int saveTag(Tag tag, int incr) {
        int res = 0;
        // 如果是子节点, 父节点也需要+1
        if (tag.getFlag() == 0) {
            if (tag.getParentId() == null) {
                tag.setParentId(0);
            }
            Tag parent = tagMapper.selectById(tag.getParentId());
            parent.setCount(sumIncr(parent.getCount(), incr));
            res = tagMapper.updateById(parent);
            if(res <= 0) {
                return -1;
            }
        }

        if (StringUtils.isBlank(String.valueOf(tag.getId())) || tag.getId() < 0) {
            res = tagMapper.insert(tag);
        } else if (tag.getCount() >= 0) {
            tag.setCount(sumIncr(tag.getCount(), incr));
            res = tagMapper.updateById(tag);
        } else {
            tag = tagMapper.selectById(tag.getId());
            tag.setCount(sumIncr(tag.getCount(), incr));
            res = tagMapper.updateById(tag);
        }
        return res;
    }

    @Override
    public List<Tag> searchTag(String keyword, int mode, int page, int pageSize) {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.like("content", keyword);
        if (mode != 2) {
            wrapper.eq("flag", mode);
        } else {
            wrapper.orderByDesc("flag");
        }
        wrapper.orderByDesc("count");
        wrapper.orderByAsc("sort");
        List<Tag> list = new ArrayList<>();
        if (page >= 0 && pageSize > 0) {
            Page<Tag> pages = new Page<>(page, pageSize);
            pages = tagMapper.selectPage(pages, wrapper);
            list = pages.getRecords();
        } else {
            list = tagMapper.selectList(wrapper);
        }
        return list;
    }

    private int sumIncr(int initNum, int incr) {
        return initNum > 0 ? initNum + incr : 0;
    }

}
