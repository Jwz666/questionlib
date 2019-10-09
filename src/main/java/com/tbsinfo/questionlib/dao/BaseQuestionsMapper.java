package com.tbsinfo.questionlib.dao;

import com.tbsinfo.questionlib.model.BaseQuestions;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 题目数据表 Mapper 接口
 * </p>
 *
 * @author jwz
 * @since 2019-09-25
 */
public interface BaseQuestionsMapper extends BaseMapper<BaseQuestions> {

    @Select("SELECT question_type FROM `quelib_base_questions` GROUP BY question_type")
    List<String> getQuestionType();
    @Select("SELECT AUTO_INCREMENT-1 AS MAXID FROM INFORMATION_SCHEMA.TABLES  WHERE TABLE_NAME = 'quelib_base_questions'")
    Integer selectMax();
}
