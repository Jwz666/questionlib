package com.tbsinfo.questionlib.vo;

import com.tbsinfo.questionlib.model.Tags;
import lombok.Data;

@Data
public class TagsAndParentTags {
    Tags sonTags;
    Tags parentTags;
}
