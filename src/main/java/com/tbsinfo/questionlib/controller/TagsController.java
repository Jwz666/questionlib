package com.tbsinfo.questionlib.controller;


import com.tbsinfo.questionlib.model.Tags;
import com.tbsinfo.questionlib.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author jwz
 * @since 2019-09-25
 */
@RestController
@RequestMapping("/tags")
public class TagsController {

    @Autowired
    private TagsService tagsService;

    @GetMapping("/getTagInfoById")
    public Tags getTagInfoById(String id) {
        if(StringUtils.isEmpty(id)) {
            return null;
        }

        return tagsService.getTagInfoById(Integer.parseInt(id));
    }
}

