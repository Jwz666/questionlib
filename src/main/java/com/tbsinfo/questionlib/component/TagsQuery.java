package com.tbsinfo.questionlib.component;

import lombok.Data;

@Data
public class TagsQuery extends BaseQuery {
    private Long id;
    private String tagType;
    private String tagName;
    private Long parentId;
}
