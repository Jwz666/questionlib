package com.tbsinfo.questionlib.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tbsinfo.questionlib.dao.QuelibQuestionTypeMapper;
import com.tbsinfo.questionlib.model.BaseQuestions;
import com.tbsinfo.questionlib.dao.BaseQuestionsMapper;
import com.tbsinfo.questionlib.model.QuelibQuestionType;
import com.tbsinfo.questionlib.service.BaseQuestionsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Autowired
    private QuelibQuestionTypeMapper quelibQuestionTypeMapper;

    @Override
    public IPage<BaseQuestions> getQuestionList(Page<BaseQuestions> page,BaseQuestions baseQuestions) {
        QueryWrapper<BaseQuestions> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("created_at");
        wrapper.setEntity(baseQuestions);
        return baseQuestionsMapper.selectPage(page, wrapper);
    }

    @Override
    public List<BaseQuestions> getQuestionList(BaseQuestions baseQuestions) {
        QueryWrapper<BaseQuestions> wrapper=new QueryWrapper<>();
        wrapper.setEntity(baseQuestions);
        return baseQuestionsMapper.selectList(wrapper);
    }

    @Override
    public List<QuelibQuestionType> getQuestionTypeList() {
        QueryWrapper<QuelibQuestionType> wrapper=new QueryWrapper<>();
        wrapper.setEntity(new QuelibQuestionType());
        return quelibQuestionTypeMapper.selectList(wrapper);
    }
}
