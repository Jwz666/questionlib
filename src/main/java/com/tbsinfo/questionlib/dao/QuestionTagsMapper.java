package com.tbsinfo.questionlib.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tbsinfo.questionlib.model.QuestionTags;
import com.tbsinfo.questionlib.model.Tags;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author jayMamba
 * @date 2019/9/29
 * @time 13:45
 * @desc
 */
public interface QuestionTagsMapper extends BaseMapper<QuestionTags> {

    @Select("SELECT qt.id,qt.tag_name,qt.tag_type,qt.parent_id\n" +
            "FROM  quelib_question_tag_relationships qtr LEFT JOIN quelib_tags qt\n" +
            "ON (qtr.tag_id = qt.id)\n" +
            "WHERE qtr.question_id=#{questionId}")
    List<Tags> getTagsByQuestionId(@Param("questionId") Long id);
}
