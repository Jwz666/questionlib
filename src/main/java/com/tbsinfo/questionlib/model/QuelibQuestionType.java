package com.tbsinfo.questionlib.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jwz
 * @since 2019-09-27
 */
public class QuelibQuestionType implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 题目类型代码
     */
    private String questionTypeCode;

    /**
     * 题目类型名称
     */
    private String questionTypeName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionTypeCode() {
        return questionTypeCode;
    }

    public void setQuestionTypeCode(String questionTypeCode) {
        this.questionTypeCode = questionTypeCode;
    }

    public String getQuestionTypeName() {
        return questionTypeName;
    }

    public void setQuestionTypeName(String questionTypeName) {
        this.questionTypeName = questionTypeName;
    }

    @Override
    public String toString() {
        return "QuelibQuestionType{" +
        "id=" + id +
        ", questionTypeCode=" + questionTypeCode +
        ", questionTypeName=" + questionTypeName +
        "}";
    }
}
