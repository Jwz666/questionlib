package com.tbsinfo.questionlib.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 标签表
 * </p>
 *
 * @author jwz
 * @since 2019-09-25
 */
@Data
@TableName("quelib_tags")
public class Tags implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 标签名
     */

    private String tagName;

    /**
     * 难度
     */
    private Integer difficult;

    /**
     * 标签类型 能力 1 知识点 2
     */
    private String tagType;

    /**
     * 父标签id
     */
    private Long parentId;

    /**
     * 创建人
     */
    private Long creatorId;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 最后更新时间
     */
    private Date updatedAt;



}
