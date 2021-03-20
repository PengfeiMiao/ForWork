package com.mpf.website.service.tag;

import com.mpf.website.entity.tag.Tag;

import java.util.List;

/**
 * @author: MiaoPengfei
 * @date: 2021/3/7 17:06
 * @description:
 * @since: 1.0.20
 */
public interface TagService {

    int deleteTags(String[] tags);

    int updateTags(String[] tags, boolean flag);

    int saveTag(Tag tag, int incr);

    List<Tag> searchTag(String keyword, int mode, int page, int pageSize);

}
