package com.tbsinfo.questionlib.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tbsinfo.questionlib.component.TagsQuery;
import com.tbsinfo.questionlib.model.Tags;
import com.tbsinfo.questionlib.dao.TagsMapper;
import com.tbsinfo.questionlib.model.UserInfo;
import com.tbsinfo.questionlib.service.TagsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tbsinfo.questionlib.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author jwz
 * @since 2019-09-25
 */
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags> implements TagsService {

    @Autowired
    private TagsMapper tagsMapper;

    @Override
    public Tags getTagInfoById(int id) {
        return tagsMapper.selectById(id);
    }
    @Override
    public IPage<Tags> getTagsList(Page<Tags> page , TagsQuery tagsQuery){
        QueryWrapper<Tags> queryWrapper=new QueryWrapper<>();
       if (tagsQuery.getTagType()!=null)queryWrapper.eq("tag_type",tagsQuery.getTagType());
        if (tagsQuery.getParentId()!=null) queryWrapper.eq("parent_id",tagsQuery.getParentId());
        return tagsMapper.selectPage(page,queryWrapper);

    }

    @Override
    public Integer updateTag(Tags tags) {
        return tagsMapper.updateById(tags);

    }


    @Override
    public Integer insertTags(Tags tag) {
        UserInfo user = UserUtil.getCurrentUser();
        tag.setCreatorId(user.getId());
        tag.setCreatedAt(new Date());
        tag.setId((long)(tagsMapper.selectCount(null)+1));
        return tagsMapper.insert(tag);
    }
}
