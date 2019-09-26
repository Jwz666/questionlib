package com.tbsinfo.questionlib.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 题目数据表
 * </p>
 *
 * @author jwz
 * @since 2019-09-25
 */
@Data
@TableName("quelib_base_questions")
public class BaseQuestions implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 题目类型: 选择 1, 简答 2
     */
    private Integer questionType;

    /**
     * 题干
     */
    private String content;

    /**
     * 选项
     */
    @TableField("optionList")
    private String optionList;

    /**
     * 答案
     */
    private String answer;

    /**
     * 解析
     */
    private String analysis;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 最后更新时间
     */
    private Date updatedAt;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 来源
     */
    private Integer source;

    /**
     * 备注
     */
    private String comment;

    /**
     * 题目设计年份
     */
    private String questionDesignTime;

    /**
     * 创建人
     */
    private Long creatorId;



}
