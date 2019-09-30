package com.tbsinfo.questionlib.component;

import lombok.Data;

import java.util.List;

/**
 * @author jayMamba
 * @date 2019/9/29
 * @time 17:26
 * @desc
 */
@Data
public class AddTagsQuery {

    private Long questionId;

    private List<Long> tagsList;

}
