package com.course.business.controller;

import com.course.server.domain.Chapter;
import com.course.server.service.ChapterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description
 * @Author Jessica
 * @Version v
 * @Date 2021/9/7
 */
@RestController
public class ChapterController {

    @Resource
    private ChapterService chapterService;

    @RequestMapping("/chapter")
    public List<Chapter> chapter() {
        return chapterService.list();
    }
}
