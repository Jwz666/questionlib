package com.tbsinfo.questionlib.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tbsinfo.questionlib.component.RetData;
import com.tbsinfo.questionlib.component.TagsQuery;
import com.tbsinfo.questionlib.dao.QuestionTagsMapper;
import com.tbsinfo.questionlib.dao.TagsGradeRelationshipsMapper;
import com.tbsinfo.questionlib.model.*;
import com.tbsinfo.questionlib.dao.TagsMapper;
import com.tbsinfo.questionlib.service.BaseQuestionsService;
import com.tbsinfo.questionlib.service.TagsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tbsinfo.questionlib.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author jwz
 * @since 2019-09-25
 */
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags> implements TagsService {

    @Autowired
    private TagsMapper tagsMapper;
    @Autowired
    private TagsGradeRelationshipsMapper tagsGradeRelationshipsMapper;
    @Autowired
    private QuestionTagsMapper questionTagsMapper;
    @Autowired
    private BaseQuestionsService baseQuestionsService;

    @Override
    public Tags getTagInfoById(int id) {
        return tagsMapper.selectById(id);
    }
    @Override
    public IPage<Tags> getTagsList(Page<Tags> page , TagsQuery tagsQuery){
        QueryWrapper<Tags> queryWrapper=new QueryWrapper<>();
        Tags tags = new Tags();

       if (tagsQuery.getTagType()!=null)tags.setTagType(tagsQuery.getTagType());
        if (tagsQuery.getParentId()!=null) tags.setParentId(tagsQuery.getParentId());
        queryWrapper.setEntity(tags);
        return tagsMapper.selectPage(page,queryWrapper);


    }

    @Override
    public Integer updateTag(Tags tags) {
        return tagsMapper.updateById(tags);

    }


    @Override
    public Integer insertTags(Tags tag) {
        UserInfo user = UserUtil.getCurrentUser();
        tag.setCreatorId(user.getId());
        tag.setCreatedAt(new Date());
        tag.setId((long)(tagsMapper.selectCount(null)+1));
        return tagsMapper.insert(tag);
    }


    @Override
    public List<Tags> getTagsInfo(Tags tags) {
        QueryWrapper<Tags> wrapper=new QueryWrapper<Tags>();
        wrapper.setEntity(tags);
        return tagsMapper.selectList(wrapper);
    }




    @Override
    public Integer deleteGradesTagRelation(String gradesId, String tagsId) {
        GradesTags gradesTags = new GradesTags();
        gradesTags.setGradeId(Long.parseLong(gradesId));
        gradesTags.setTagId(Long.parseLong(tagsId));
        QueryWrapper<GradesTags> queryWrapper =new QueryWrapper<>();
        queryWrapper.setEntity(gradesTags);
        Long id = tagsGradeRelationshipsMapper.selectList(queryWrapper).get(0).getId();
        QueryWrapper<GradesTags> queryWrapper1 =new QueryWrapper<>();
        queryWrapper1.eq("id",id);
        return tagsGradeRelationshipsMapper.delete(queryWrapper1);

    }

    @Override
    public List<Tags> getTagsByTypeAndParentId(String tagType, String parentId) {
        QueryWrapper<Tags> queryWrapper=new QueryWrapper();
        if (tagType!=null) queryWrapper.eq("tag_type",tagType);
        if (parentId!=null) queryWrapper.eq("parent_id",parentId);
        return tagsMapper.selectList(queryWrapper);
    }

    @Override
    public Integer insertGradesTags(GradesTags gt) {
        return tagsGradeRelationshipsMapper.insert(gt);
    }
    @Override
    public List<Tags> getTagsByGradesId(int id) {
        QueryWrapper<GradesTags> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("grade_id",id);
        List<GradesTags> gradesTags = tagsGradeRelationshipsMapper.selectList(queryWrapper);
        List<Tags> tagsList=new ArrayList<>();
        for (GradesTags gt:gradesTags) {
            tagsList.add(tagsMapper.selectById(gt.getTagId())) ;
        }
        return tagsList;
    }



    @Override
    @Transactional
    public Integer insertQuestionsTags(QuestionTags qt) {
        BaseQuestions baseQuestions = baseQuestionsService.getById(qt.getQuestionId());
        if(baseQuestions.getStatus()!=0) {
            baseQuestions.setStatus(1);
        }
        return questionTagsMapper.insert(qt);
    }

    @Override
    public Integer deleteQuestionsTagRelation(String questionsId, String tagsId) {
        QuestionTags questionTags = new QuestionTags();
        questionTags.setQuestionId((Long.parseLong(questionsId)));
        questionTags.setTagId(Long.parseLong(tagsId));
        QueryWrapper<QuestionTags> queryWrapper =new QueryWrapper<>();
        queryWrapper.setEntity(questionTags);
        Long id = questionTagsMapper.selectList(queryWrapper).get(0).getId();
        QueryWrapper<QuestionTags> queryWrapper1 =new QueryWrapper<>();
        queryWrapper1.eq("id",id);
        return questionTagsMapper.delete(queryWrapper1);
    }

    @Override
    public List<Tags> getTagsByQuestionId(int parseInt) {
        QueryWrapper<QuestionTags> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("question_id",parseInt);
        List<QuestionTags> gradesTags = questionTagsMapper.selectList(queryWrapper);
        List<Tags> tagsList=new ArrayList<>();
        for (QuestionTags gt:gradesTags) {
            tagsList.add(tagsMapper.selectById(gt.getTagId())) ;
        }
        return tagsList;
    }



}
