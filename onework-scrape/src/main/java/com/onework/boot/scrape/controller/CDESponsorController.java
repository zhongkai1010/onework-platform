package com.onework.boot.scrape.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.onework.boot.framework.common.pojo.CommonResult;
import com.onework.boot.framework.common.pojo.PageResult;
import com.onework.boot.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.onework.boot.framework.mybatis.core.util.MyBatisUtils;
import com.onework.boot.scrape.controller.vo.CDESponsorPageReqVO;
import com.onework.boot.scrape.controller.vo.CDESponsorReqVO;
import com.onework.boot.scrape.dal.dataobject.CDESponsor;
import com.onework.boot.scrape.service.CDESponsorService;
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
 * @since 2025-01-13
 */
@RestController
@RequestMapping("/scrape/cde-sponsor")
public class CDESponsorController {
    private final CDESponsorService service;

    public CDESponsorController(CDESponsorService service) {
        this.service = service;
    }

    @Operation(description = "新增或删除", summary = "新增或删除")
    @PostMapping("addOrUpdate")
    public CommonResult<CDESponsor> addOrUpdate(@RequestBody CDESponsor params) {
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
    public CommonResult<CDESponsor> get(@PathVariable String id) {
        return CommonResult.success(service.getById(id));
    }

    @Operation(description = "查询列表", summary = "查询列表")
    @GetMapping("getAll")
    public CommonResult<List<CDESponsor>> getAll(@Validated CDESponsorReqVO reqVO) {
        LambdaQueryWrapperX<CDESponsor> queryWrapper = new LambdaQueryWrapperX<>();
        List<CDESponsor> resultData = service.list(queryWrapper);
        return CommonResult.success(resultData);
    }

    @Operation(description = "分页查询", summary = "分页查询")
    @GetMapping("getPageAll")
    public CommonResult<PageResult<CDESponsor>> getPageList(@Validated CDESponsorPageReqVO pageReqVO) {

        Page<CDESponsor> page = MyBatisUtils.buildPage(pageReqVO);
        LambdaQueryWrapperX<CDESponsor> queryWrapper = new LambdaQueryWrapperX<>();
        long total = service.count(queryWrapper);
        List<CDESponsor> data = service.list(page, queryWrapper);
        PageResult<CDESponsor> pageResult = new PageResult<>(data, total);
        return CommonResult.success(pageResult);
    }
}
