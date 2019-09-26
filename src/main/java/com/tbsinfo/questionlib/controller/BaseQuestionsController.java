package com.tbsinfo.questionlib.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tbsinfo.questionlib.component.QuestionQuery;
import com.tbsinfo.questionlib.component.RetData;
import com.tbsinfo.questionlib.model.BaseQuestions;
import com.tbsinfo.questionlib.service.BaseQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * 题目数据表 前端控制器
 * </p>
 *
 * @author jwz
 * @since 2019-09-25
 */
@RestController
@RequestMapping("/baseQuestions")
public class BaseQuestionsController {

    @Autowired
    private BaseQuestionsService questionsService;

    @GetMapping("/getQuestionByPage")
    public RetData getQuestionByPage( QuestionQuery questionQuery) {
        //ModelAndView modelAndView=new ModelAndView("math-exe.html");
        Page<BaseQuestions> page=new Page<>(questionQuery.getPage(), questionQuery.getSize());
        //modelAndView.addObject("Questions", page);
        return new RetData().success(questionsService.getQuestionList(page));
    }

}

