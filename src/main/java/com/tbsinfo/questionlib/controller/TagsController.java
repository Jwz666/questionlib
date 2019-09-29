package com.tbsinfo.questionlib.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tbsinfo.questionlib.component.RetData;
import com.tbsinfo.questionlib.component.TagsQuery;
import com.tbsinfo.questionlib.model.Tags;
import com.tbsinfo.questionlib.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @GetMapping("/getTagsByPage")
    public RetData getTagsByPage(TagsQuery tagsQuery) {
        //ModelAndView modelAndView=new ModelAndView("math-exe.html");
        Page<Tags> page=new Page<>(tagsQuery.getPage(), tagsQuery.getSize());
        //modelAndView.addObject("Questions", page);
        return new RetData().success(tagsService.getTagsList(page));
    }
    @PostMapping("/updateTag")
    public RetData<String> updateTag(Tags tags){
        Integer status= tagsService.updateTag(tags);
        if (status==1){
            return new RetData().success("编辑成功");
        }else{
            return new RetData().erro("500","编辑失败");
        }

    }

}

