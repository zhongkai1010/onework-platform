package com.onework.boot.scrape.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.onework.boot.framework.common.pojo.CommonResult;
import com.onework.boot.framework.common.pojo.PageResult;
import com.onework.boot.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.onework.boot.framework.mybatis.core.util.MyBatisUtils;
import com.onework.boot.scrape.controller.vo.CTRProjectPageReqVO;
import com.onework.boot.scrape.controller.vo.CTRProjectReqVO;
import com.onework.boot.scrape.dal.dataobject.CTRProject;
import com.onework.boot.scrape.service.CTRProjectService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * <p>
 * 临床试验项目表 前端控制器
 * </p>
 *
 * @author onework
 * @since 2025-01-13
 */
@RestController
@RequestMapping("/scrape/ctr-project")
public class CTRProjectController {
    private final CTRProjectService service;

    public CTRProjectController(CTRProjectService service) {
        this.service = service;
    }

    @Operation(description = "新增或删除", summary = "新增或删除")
    @PostMapping("addOrUpdate")
    public CommonResult<CTRProject> addOrUpdate(@RequestBody CTRProject params) {
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
    public CommonResult<CTRProject> get(@PathVariable String id) {
        return CommonResult.success(service.getById(id));
    }

    @Operation(description = "查询列表", summary = "查询列表")
    @GetMapping("getAll")
    public CommonResult<List<CTRProject>> getAll(@Validated CTRProjectReqVO reqVO) {
        LambdaQueryWrapperX<CTRProject> queryWrapper = new LambdaQueryWrapperX<>();
        List<CTRProject> resultData = service.list(queryWrapper);
        return CommonResult.success(resultData);
    }

    @Operation(description = "分页查询", summary = "分页查询")
    @GetMapping("getPageAll")
    public CommonResult<PageResult<CTRProject>> getPageList(@Validated CTRProjectPageReqVO pageReqVO) {

        Page<CTRProject> page = MyBatisUtils.buildPage(pageReqVO);
        LambdaQueryWrapperX<CTRProject> queryWrapper = new LambdaQueryWrapperX<>();
        long total = service.count(queryWrapper);
        List<CTRProject> data = service.list(page, queryWrapper);
        PageResult<CTRProject> pageResult = new PageResult<>(data, total);
        return CommonResult.success(pageResult);
    }
}
