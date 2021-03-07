package com.mpf.website.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: MiaoPengfei
 * @date: 2021/3/7 15:51
 * @description:
 * @since: 1.0.20
 */
@RestController
@RequestMapping("/comment")
@Api(value = "评论接口", tags = "评论接口")
public class CommentController {
}
