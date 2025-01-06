package com.onework.boot.scrape.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.onework.boot.scrape.dal.dataobject.CDEProject;
import com.onework.boot.scrape.dal.mysql.CDEProjectMapper;
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
