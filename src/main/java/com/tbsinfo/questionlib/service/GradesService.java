package com.tbsinfo.questionlib.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tbsinfo.questionlib.component.GradesQuery;
import com.tbsinfo.questionlib.model.Grades;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tbsinfo.questionlib.model.Tags;

import java.util.List;

/**
 * <p>
 * 年级表 服务类
 * </p>
 *
 * @author jwz
 * @since 2019-09-25
 */
public interface GradesService extends IService<Grades> {
    public IPage<Grades> getGradesList(Page<Grades> page , GradesQuery gradesQuery);

    public Grades getGradeInfoById(int id) ;



}
