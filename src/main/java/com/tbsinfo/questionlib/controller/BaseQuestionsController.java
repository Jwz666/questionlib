package com.tbsinfo.questionlib.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tbsinfo.questionlib.component.AddTagsQuery;
import com.tbsinfo.questionlib.component.QuestionQuery;
import com.tbsinfo.questionlib.component.RetData;
import com.tbsinfo.questionlib.dao.QuestionTagsMapper;
import com.tbsinfo.questionlib.model.BaseQuestions;
import com.tbsinfo.questionlib.model.Tags;
import com.tbsinfo.questionlib.service.BaseQuestionsService;
import com.tbsinfo.questionlib.service.TagsService;
import com.tbsinfo.questionlib.utils.HTMLUntil;
import com.tbsinfo.questionlib.vo.TagsAndParentTags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Autowired
    TagsService tagsService;


    /**
     * 分页条件获取题目信息
     * @param questionQuery
     * @return
     */
    @GetMapping("/getQuestionByPage")
    public RetData getQuestionByPage( QuestionQuery questionQuery) {
        Page<BaseQuestions> page=new Page<>(questionQuery.getPage(), questionQuery.getSize());
        BaseQuestions baseQuestions=new BaseQuestions();
        baseQuestions.setId(questionQuery.getId());
        baseQuestions.setQuestionType(questionQuery.getQuestionType());
        baseQuestions.setStatus(questionQuery.getStatus());
        IPage<BaseQuestions> iPage=questionsService.getQuestionList(page,baseQuestions);
        HTMLUntil.dealBaseQuestionList(iPage.getRecords());
        return new RetData().success(iPage);
    }

    /**
     * 条件获取题目信息
     * @param baseQuestions
     * @return
     */
    @GetMapping("/getQuestionList")
    public RetData getQuestionList(BaseQuestions baseQuestions) {
        return new RetData().success(questionsService.getQuestionList(baseQuestions));
    }

    /**
     * 获取题目类型
     * @return
     */
    @GetMapping("/getQuestionType")
    public RetData getQuestionType() {
        return new RetData().success(questionsService.getQuestionTypeList());
    }

    /**
     * 新增获取修改题目
     * @param baseQuestions
     * @return
     */
    @PostMapping("/addOrUpdateQuestion")
    public RetData addOrUpdateQuestion(@RequestBody BaseQuestions baseQuestions) {
        baseQuestions.setUpdatedAt(new Date());
        baseQuestions.setSource(1);
        boolean res=false;
        if(baseQuestions.getId()==null) {
            baseQuestions.setCreatedAt(new Date());
            baseQuestions.setStatus(0);

        }
            res = questionsService.saveOrUpdate(baseQuestions);

            if (res) {
                return new RetData().success(baseQuestions);
            }


        return new RetData().erro("500", "操作失败，请稍后重试");
    }

    /**
     * 获取题目相关标签
     * @param id
     * @return
     */
    @GetMapping("/getQuestionTags")
    public RetData getQuestionTags(Long id) {
        if(id==null) {
            return new RetData().erro("500", "请传入题目id");
        }
        return new RetData().success(questionsService.getTagsByQuestionId(id));
    }

    @PostMapping("/addTagsToQuestion")
    public RetData addTagsToQuestion(@RequestBody AddTagsQuery addTagsQuery) {
        return questionsService.addTags(addTagsQuery.getQuestionId(),addTagsQuery.getTagsList());
    }
    @PostMapping("/deleteQuestionsTag")
    public  RetData deleteQuestionsTag(@RequestParam("gradesId")String questionsId,@RequestParam("tagsId")String tagsId){
        Integer status= tagsService.deleteQuestionsTagRelation(questionsId,tagsId);
        if (status==1)  return new RetData().success("删除成功") ;
        return new RetData().erro("500","删除失败");
    }
    @GetMapping("/getTagsByQuestionId")
    public RetData getTagsByQuestionId(@RequestParam("id") String id) {

        List<Tags> page= tagsService.getTagsByQuestionId(Integer.parseInt(id));
        return new RetData().success(getParentAndSonTags(page, tagsService));
    }

    public static List<TagsAndParentTags> getParentAndSonTags(List<Tags> page, TagsService tagsService) {
        List<TagsAndParentTags>tagsAndParentTagsList =new ArrayList<>();
        for (Tags tags:page){
            TagsAndParentTags tagsAndParentTags= new TagsAndParentTags();
            tagsAndParentTags.setSonTags(tags);
            if (tags.getParentId()!=0){
                tagsAndParentTags.setParentTags(tagsService.getById(tags.getParentId()));
            }
            tagsAndParentTagsList.add(tagsAndParentTags);

        }
        return tagsAndParentTagsList;
    }


}

