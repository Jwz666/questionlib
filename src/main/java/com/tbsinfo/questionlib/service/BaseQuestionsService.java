package com.tbsinfo.questionlib.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tbsinfo.questionlib.component.RetData;
import com.tbsinfo.questionlib.model.BaseQuestions;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 题目数据表 服务类
 * </p>
 *
 * @author jwz
 * @since 2019-09-25
 */
public interface BaseQuestionsService extends IService<BaseQuestions> {

    IPage<BaseQuestions> getQuestionList(Page<BaseQuestions> page);
}
