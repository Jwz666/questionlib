package com.tbsinfo.questionlib.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tbsinfo.questionlib.component.TagsQuery;
import com.tbsinfo.questionlib.model.Tags;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author jwz
 * @since 2019-09-25
 */
public interface TagsService extends IService<Tags> {

    Tags getTagInfoById(int id);

    IPage<Tags> getTagsList(Page<Tags> page);

    Integer updateTag(Tags tags);

    IPage<Tags> getChildrenTags(Page<Tags> page, TagsQuery tagsQuery);

    Integer insertTags(Tags tag);
}
