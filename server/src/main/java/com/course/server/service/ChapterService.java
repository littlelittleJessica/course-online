package com.course.server.service;

import com.course.server.domain.Chapter;
import com.course.server.domain.ChapterExample;
import com.course.server.dto.ChapterDto;
import com.course.server.dto.ChapterPageDto;
import com.course.server.mapper.ChapterMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description
 * @Author Jessica
 * @Version v
 * @Date 2021/9/7
 */
@Service
public class ChapterService {

    @Resource
    private ChapterMapper chapterMapper;

    /**
     * 查询
     *
     * @param chapterPageDto 传入的是分页实体
     *                       包含了前端传入的page和size，同时将后端查询的total和list放入该实体中
     */
    public void list(ChapterPageDto chapterPageDto) {
        PageHelper.startPage(chapterPageDto.getPage(), chapterPageDto.getSize());
        ChapterExample chapterExample = new ChapterExample();
        ChapterExample.Criteria criteria = chapterExample.createCriteria();
        if (!StringUtils.isEmpty(chapterPageDto.getCourseId())) {
            criteria.andCourseIdEqualTo(chapterPageDto.getCourseId());
        }
        List<Chapter> chapterList = chapterMapper.selectByExample(chapterExample);
        PageInfo<Chapter> pageInfo = new PageInfo<>(chapterList);
        chapterPageDto.setTotal(pageInfo.getTotal());
        List<ChapterDto> chapterDtoList = CopyUtil.copyList(chapterList, ChapterDto.class);
        chapterPageDto.setList(chapterDtoList);
    }

    /**
     * 保存
     *
     * @param chapterDto 传入新增的chapter实体
     */
    public void save(ChapterDto chapterDto) {
        Chapter chapter = CopyUtil.copy(chapterDto, Chapter.class);
        if (StringUtils.isEmpty(chapterDto.getId())) {
            this.insert(chapter);
        } else {
            this.update(chapter);
        }
    }

    /**
     * 新增
     *
     * @param chapter 传入新增的chapter实体
     */
    private void insert(Chapter chapter) {
        chapter.setId(UuidUtil.getShortUuid());
        chapterMapper.insert(chapter);
    }

    /**
     * 更新
     *
     * @param chapter 传入更新的chapter实体
     */
    private void update(Chapter chapter) {
        chapterMapper.updateByPrimaryKey(chapter);
    }

    /**
     * 删除
     *
     * @param id 传入更新的chapter实体
     */
    public void delete(String id) {
        chapterMapper.deleteByPrimaryKey(id);
    }

    /**
     * 查询某一课程下的所有大章
     */
    public List<ChapterDto> listByCourse(String id){
        ChapterExample example = new ChapterExample();
        example.createCriteria().andCourseIdEqualTo(id);
        List<Chapter> chapterList = chapterMapper.selectByExample(example);
        return CopyUtil.copyList(chapterList,ChapterDto.class);
    }
}