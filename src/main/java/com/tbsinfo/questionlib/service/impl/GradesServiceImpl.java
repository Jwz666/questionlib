package com.tbsinfo.questionlib.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tbsinfo.questionlib.component.GradesQuery;
import com.tbsinfo.questionlib.dao.GradesTagsMapper;
import com.tbsinfo.questionlib.model.Grades;
import com.tbsinfo.questionlib.dao.GradesMapper;
import com.tbsinfo.questionlib.model.GradesTags;
import com.tbsinfo.questionlib.model.Tags;
import com.tbsinfo.questionlib.service.GradesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 年级表 服务实现类
 * </p>
 *
 * @author jwz
 * @since 2019-09-25
 */
@Service
public class GradesServiceImpl extends ServiceImpl<GradesMapper, Grades> implements GradesService {
    @Autowired
    GradesMapper gradesMapper;
    @Autowired
    GradesTagsMapper gradesTagsMapper;
    @Override
    public IPage<Grades> getGradesList(Page<Grades> page , GradesQuery gradesQuery){
        QueryWrapper<Grades> queryWrapper=new QueryWrapper<>();
        return gradesMapper.selectPage(page,queryWrapper);

    }

    @Override
    public Grades getGradeInfoById(int parseInt) {
        return gradesMapper.selectById(parseInt);
    }

    @Override
    public Page<Tags> getTagsByGradesId(int id) {
        // 等待
        return null;
    }

}
