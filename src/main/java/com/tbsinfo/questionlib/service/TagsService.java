package com.tbsinfo.questionlib.service;

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
}
