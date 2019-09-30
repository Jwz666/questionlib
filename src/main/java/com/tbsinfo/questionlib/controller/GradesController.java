package com.tbsinfo.questionlib.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tbsinfo.questionlib.component.GradesQuery;
import com.tbsinfo.questionlib.component.RetData;
import com.tbsinfo.questionlib.model.Grades;
import com.tbsinfo.questionlib.model.Tags;
import com.tbsinfo.questionlib.service.GradesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

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
    @GetMapping("/getGradesInfoById")
    public Grades getGradesInfoById(String gradeid) {
        if(StringUtils.isEmpty(gradeid)) {
            return null;
        }

        return gradesService.getGradeInfoById (Integer.parseInt(gradeid));
    }
    @GetMapping("/getTagsByGradesId")
    public RetData getTagsFromGradesTags(@RequestParam("id") String id) {

        Page<Tags> page= gradesService.getTagsByGradesId(Integer.parseInt(id));
        return new RetData().success(page);
    }
}

