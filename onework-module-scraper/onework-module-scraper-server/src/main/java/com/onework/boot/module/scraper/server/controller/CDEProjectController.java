package com.onework.boot.module.scraper.server.controller;

import com.onework.boot.module.scraper.core.dal.dataobject.CDEProject;
import com.onework.boot.module.scraper.core.service.CDEProjectService;
import com.onework.boot.module.scraper.server.controller.vo.CDEProjectPageReqVO;
import com.onework.boot.module.scraper.server.controller.vo.CDEProjectReqVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.onework.boot.framework.common.pojo.CommonResult;
import com.onework.boot.framework.common.pojo.PageResult;
import com.onework.boot.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.onework.boot.framework.mybatis.core.util.MyBatisUtils;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author onework
 * @since 2025-05-09
 */
@RestController
@RequestMapping("/cde-project")
public class CDEProjectController {
    private final CDEProjectService service;

    public CDEProjectController(CDEProjectService service) {
        this.service = service;
    }

    @Operation(description = "新增或删除", summary = "新增或删除")
    @PostMapping("addOrUpdate")
    public CommonResult<CDEProject> addOrUpdate(@RequestBody CDEProject params) {
        service.saveOrUpdate(params);
        return CommonResult.success(params);
    }

    @Operation(description = "移除", summary = "移除")
    @PostMapping("remove/{id}")
    public CommonResult<Boolean> remove(@PathVariable String id) {
        return CommonResult.success(service.removeById(id));
    }

    @Operation(description = "获取详情", summary = "获取详情")
    @GetMapping("get/{id}")
    public CommonResult<CDEProject> get(@PathVariable String id) {
        return CommonResult.success(service.getById(id));
    }

    @Operation(description = "查询列表", summary = "查询列表")
    @GetMapping("getAll")
    public CommonResult<List<CDEProject>> getAll(@Validated CDEProjectReqVO reqVO) {
        LambdaQueryWrapperX<CDEProject> queryWrapper = new LambdaQueryWrapperX<>();
        List<CDEProject> resultData = service.list(queryWrapper);
        return CommonResult.success(resultData);
    }

    @Operation(description = "分页查询", summary = "分页查询")
    @GetMapping("getPageAll")
    public CommonResult<PageResult<CDEProject>> getPageList(@Validated CDEProjectPageReqVO pageReqVO) {

        Page<CDEProject> page = MyBatisUtils.buildPage(pageReqVO);
        LambdaQueryWrapperX<CDEProject> queryWrapper = new LambdaQueryWrapperX<>();
        long total = service.count(queryWrapper);
        List<CDEProject> data = service.list(page, queryWrapper);
        PageResult<CDEProject> pageResult = new PageResult<>(data, total);
        return CommonResult.success(pageResult);
    }
}
