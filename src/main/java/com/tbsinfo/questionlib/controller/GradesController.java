package com.tbsinfo.questionlib.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tbsinfo.questionlib.component.GradesQuery;
import com.tbsinfo.questionlib.component.RetData;
import com.tbsinfo.questionlib.model.Grades;
import com.tbsinfo.questionlib.model.Tags;
import com.tbsinfo.questionlib.service.GradesService;
import com.tbsinfo.questionlib.service.TagsService;
import com.tbsinfo.questionlib.vo.TagsAndParentTags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

import static com.tbsinfo.questionlib.controller.BaseQuestionsController.getParentAndSonTags;

/**
 * <p>
 * 年级表 前端控制器
 * </p>
 *
 * @author jwz
 * @since 2019-09-25
 */
@RestController
@RequestMapping("/grades")
public class GradesController {
    @Autowired
    GradesService gradesService;
    @Autowired
    TagsService tagsService;

    @GetMapping("/getGradesByPage")
    public RetData getGradesByPage(GradesQuery gradesQuery) {
        //ModelAndView modelAndView=new ModelAndView("math-exe.html");
        Page<Grades> page=new Page<>(gradesQuery.getPage(), gradesQuery.getSize());
        //modelAndView.addObject("Questions", page);
        return new RetData().success(gradesService.getGradesList(page,gradesQuery));
    }

    @PostMapping("/readyToEdit")
    public RetData readyToEdit(@RequestParam("id") String gradeid){
        Grades grades = getGradesInfoById(gradeid);

        if (grades!=null)return new RetData().success("");
        return new RetData().erro("500","");
    }
    @GetMapping("/getGradeInfoById")
    public Grades getGradesInfoById(@RequestParam("id")String gradeid) {
        if(StringUtils.isEmpty(gradeid)) {
            return null;
        }

        return gradesService.getGradeInfoById (Integer.parseInt(gradeid));
    }
    @GetMapping("/getTagsByGradesId")
    public RetData getTagsFromGradesTags(@RequestParam("id") String id) {

        List<Tags> page= tagsService.getTagsByGradesId(Integer.parseInt(id));
        return new RetData().success(getParentAndSonTags(page, tagsService));
    }
    @PostMapping("/deleteGradesTag")
    public  RetData deleteGradesTag(@RequestParam("gradesId")String gradesId,@RequestParam("tagsId")String tagsId){
        Integer status= tagsService.deleteGradesTagRelation(gradesId,tagsId);
          if (status==1)  return new RetData().success("删除成功") ;
          return new RetData().erro("500","删除失败");
    }
}

