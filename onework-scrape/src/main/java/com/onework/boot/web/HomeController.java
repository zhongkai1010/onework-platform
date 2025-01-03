package com.onework.boot.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.onework.boot.scrape.data.entity.CDEProject;
import com.onework.boot.scrape.data.mapper.CDEProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {

    @Autowired
    private CDEProjectMapper projectMapper;

    @RequestMapping("/")
    List<CDEProject> home() {
        Page<CDEProject> page = new Page<>(1, 1);
        projectMapper.selectPage(page, null);

        return page.getRecords();
    }
}
