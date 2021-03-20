package com.mpf.website.controller;

import com.mpf.website.entity.rest.RestResponse;
import com.mpf.website.entity.tag.Tag;
import com.mpf.website.service.tag.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: MiaoPengfei
 * @date: 2021/3/7 15:46
 * @description:
 * @since: 1.0.20
 */
@Slf4j
@RestController
@RequestMapping("/tag")
@Api(value = "标签接口", tags = "标签接口")
public class TagController {

    @Autowired
    TagService tagService;

    @ApiOperation(value = "保存标签", notes = "保存标签")
    @PostMapping("/save")
    @ResponseBody
    public RestResponse saveTag(@ApiParam("标签数据") @RequestBody Tag tag) {
        if (log.isInfoEnabled()) {
            log.info("saveTag: {}", tag);
        }
        RestResponse response = new RestResponse();
        try {
            Integer res = tagService.saveTag(tag, 1);
            response.setStatus(RestResponse.SUCCESS);
            response.setMessage("success");
            response.setData(res);
        } catch (Exception e) {
            log.error("saveTag fail,message:{}", e.getMessage());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @ApiOperation(value = "根据关键字搜索标签", notes = "根据关键字搜索标签")
    @GetMapping("/search")
    @ResponseBody
    public RestResponse searchTag(@ApiParam("关键字") @RequestParam("keyword") String keyword,
                                   @ApiParam("模式：0-查标签，1-查目录，2-混合查询") @RequestParam(value = "mode", defaultValue = "2") Integer mode,
                                   @ApiParam("分页页码") @RequestParam(value = "page", defaultValue = "0") Integer page,
                                   @ApiParam("分页大小") @RequestParam(value = "pageSize", defaultValue = "0") Integer pageSize) {
        if (log.isInfoEnabled()) {
            log.info("searchTag, keyword:{}, mode: {}", keyword, mode);
        }
        RestResponse response = new RestResponse();
        try {
            List<Tag> res = tagService.searchTag(keyword, mode, page, pageSize);
            response.setStatus(RestResponse.SUCCESS);
            response.setMessage("success");
            response.setData(res);
        } catch (Exception e) {
            log.error("searchTag fail,message:{}", e.getMessage());
            response.setMessage(e.getMessage());
        }
        return response;
    }
    
}
