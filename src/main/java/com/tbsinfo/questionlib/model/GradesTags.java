package com.tbsinfo.questionlib.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class GradesTags {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("grade_id")
    private Long gradeId;

    @TableField("tag_id")
    private Long tagId;
}
