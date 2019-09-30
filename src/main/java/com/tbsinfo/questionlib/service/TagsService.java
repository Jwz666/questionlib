package com.tbsinfo.questionlib.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tbsinfo.questionlib.component.TagsQuery;
import com.tbsinfo.questionlib.model.GradesTags;
import com.tbsinfo.questionlib.model.QuestionTags;
import com.tbsinfo.questionlib.model.Tags;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author jwz
 * @since 2019-09-25
 */
public interface TagsService extends IService<Tags> {

    Tags getTagInfoById(int id);

    IPage<Tags> getTagsList(Page<Tags> page, TagsQuery tagsQuery);

    Integer updateTag(Tags tags);

    List<Tags> getTagsByGradesId (int id);

    Integer insertTags(Tags tag);

    Integer deleteGradesTagRelation(String gradesId, String tagsId);

    List<Tags> getTagsByTypeAndParentId(String tagType, String parentId);

    Integer insertGradesTags(GradesTags gt);

    Integer insertQuestionsTags(QuestionTags qt);

    Integer deleteQuestionsTagRelation(String questionsId, String tagsId);

    List<Tags> getTagsByQuestionId(int parseInt);
}
