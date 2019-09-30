package com.tbsinfo.questionlib.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tbsinfo.questionlib.component.RetData;
import com.tbsinfo.questionlib.component.TagsQuery;
import com.tbsinfo.questionlib.model.Tags;
import com.tbsinfo.questionlib.service.TagsService;
import com.tbsinfo.questionlib.utils.GUID;
import com.tbsinfo.questionlib.utils.RequestUtil;
import com.tbsinfo.questionlib.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
        return new RetData().success(tagsService.getTagsList(page,tagsQuery));
    }
    @PostMapping("/updateTag")
    public RetData<String> updateTag(Tags tags){
        Integer status= tagsService.updateTag(tags);
        if (status==1){

            return new RetData().erro("200","编辑成功");
        }else{
            return new RetData().erro("500","编辑失败");
        }
    }

    @PostMapping("/readyToEdit")
    public RetData readyToEdit(@RequestParam("id") String tagid){
        Tags tag = getTagInfoById(tagid);

        if (tag!=null)return new RetData().success("");
        return new RetData().erro("500","");
    }

    @PostMapping("/insertTags")
    public RetData insertTags(Tags tag) {
        //ModelAndView modelAndView=new ModelAndView("math-exe.html");
        //modelAndView.addObject("Questions", page);
        tag.setDifficult(0);//默认值
        int status=tagsService.insertTags(tag);
        if (status==1)return new RetData().erro("200","添加成功");
        return new RetData().erro("500","添加失败");
    }


    /**
     * 条件获取标签信息
     * @param tags
     * @return
     */
    @GetMapping("/getTagsInfo")
    public RetData getTagsInfo(Tags tags) {
        return new RetData().success(tagsService.getTagsInfo(tags));
    }

}

