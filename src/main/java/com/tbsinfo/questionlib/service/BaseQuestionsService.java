package com.tbsinfo.questionlib.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tbsinfo.questionlib.component.RetData;
import com.tbsinfo.questionlib.model.BaseQuestions;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tbsinfo.questionlib.model.QuelibQuestionType;
import com.tbsinfo.questionlib.model.QuestionTags;
import com.tbsinfo.questionlib.model.Tags;

import java.util.List;

/**
 * <p>
 * 题目数据表 服务类
 * </p>
 *
 * @author jwz
 * @since 2019-09-25
 */
public interface BaseQuestionsService extends IService<BaseQuestions> {

    IPage<BaseQuestions> getQuestionList(Page<BaseQuestions> page,BaseQuestions baseQuestions);

    List<BaseQuestions> getQuestionList(BaseQuestions baseQuestions);

    List<QuelibQuestionType> getQuestionTypeList();

    List<Tags> getTagsByQuestionId(Long id);

    RetData addTags(Long questionId,List<Long> tagsList);

}
