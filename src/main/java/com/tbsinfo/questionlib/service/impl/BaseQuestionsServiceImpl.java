package com.tbsinfo.questionlib.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tbsinfo.questionlib.component.RetData;
import com.tbsinfo.questionlib.dao.QuelibQuestionTypeMapper;
import com.tbsinfo.questionlib.dao.QuestionTagsMapper;
import com.tbsinfo.questionlib.dao.TagsMapper;
import com.tbsinfo.questionlib.model.BaseQuestions;
import com.tbsinfo.questionlib.dao.BaseQuestionsMapper;
import com.tbsinfo.questionlib.model.QuelibQuestionType;
import com.tbsinfo.questionlib.model.QuestionTags;
import com.tbsinfo.questionlib.service.BaseQuestionsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tbsinfo.questionlib.model.Tags;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @Autowired
    private QuestionTagsMapper questionTagsMapper;


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

    @Override
    public List<Tags> getTagsByQuestionId(Long id) {
        List<Tags> tagsList=questionTagsMapper.getTagsByQuestionId(id);
        return getTagTree(tagsList);
    }

    private List<Tags> getTagTree(List<Tags> tags) {
        List<Tags> rootNode=new ArrayList<>();
        for(Tags tag:tags) {
            if(tag.getParentId()==0) {
                rootNode.add(tag);

            }
        }
        fillChildNode(tags,rootNode);
        return rootNode;
    }

    private void fillChildNode(List<Tags> tagsSet,List<Tags> Nodes) {
        if(Nodes==null||Nodes.size()==0) {
            return ;
        }
        List<Tags> childTags=new ArrayList<>();
        for(Tags tag:Nodes) {
            for(Tags setTag:tagsSet) {
                if(setTag.getParentId()==tag.getId()) {
                    childTags.add(tag);
                }
            }
            fillChildNode(tagsSet,childTags);
            tag.setChildTags(childTags);
        }


    }

    @Transactional
    @Override
    public RetData addTags(Long questionId,List<Long> tagsList) {
        QueryWrapper<QuestionTags> wrapper=new QueryWrapper<>();
        QuestionTags questionTags=new QuestionTags();
        questionTags.setQuestionId(questionId);
        try {
            questionTagsMapper.delete(wrapper);
            for(Long id:tagsList) {
                QuestionTags questionTags1=new QuestionTags();
                questionTags1.setQuestionId(questionId);
                questionTags1.setTagId(id);
                questionTagsMapper.insert(questionTags1);
            }
            return new RetData().success(null);
        } catch (Exception e) {
            return new RetData().erro("500", "发生未知错误，请稍后重试");
        }


    }

}
