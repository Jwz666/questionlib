package com.tbsinfo.questionlib.service.impl;

import com.tbsinfo.questionlib.model.Tags;
import com.tbsinfo.questionlib.dao.TagsMapper;
import com.tbsinfo.questionlib.service.TagsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
