package com.tbsinfo.questionlib.component;

import lombok.Data;

/**
 * @author jayMamba
 * @date 2019/9/25
 * @time 18:41
 * @desc
 */
@Data
public class QuestionQuery extends BaseQuery{


    /**
     * 题目id
     */
    private Long id;

    /**
     * 题目类型: 选择 1, 简答 2
     */
    private Integer questionType;

    private Integer status;

    /**
     * 题干
     */
    private String content;

}
