package com.tbsinfo.questionlib.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author jayMamba
 * @date 2019/9/29
 * @time 13:42
 * @desc
 */
@Data
@TableName("quelib_question_tag_relationships")
public class QuestionTags {
    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("question_id")
    private Long questionId;

    @TableField("tag_id")
    private Long tagId;
}
