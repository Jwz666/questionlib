package com.tbsinfo.questionlib.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tbsinfo.questionlib.model.BaseQuestions;
import com.tbsinfo.questionlib.dao.BaseQuestionsMapper;
import com.tbsinfo.questionlib.service.BaseQuestionsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 题目数据表 服务实现类
 * </p>
 *
 * @author jwz
 * @since 2019-09-25
 */
@Service
public class BaseQuestionsServiceImpl extends ServiceImpl<BaseQuestionsMapper, BaseQuestions> implements BaseQuestionsService {

    @Autowired
    private BaseQuestionsMapper baseQuestionsMapper;

    @Override
    public IPage<BaseQuestions> getQuestionList(Page<BaseQuestions> page) {
        return baseQuestionsMapper.selectPage(page, null);
    }
}
