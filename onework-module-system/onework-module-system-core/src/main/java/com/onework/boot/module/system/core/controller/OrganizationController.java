package com.onework.boot.module.system.core.controller;

import com.onework.boot.module.system.core.controller.vo.OrganizationPageReqVO;
import com.onework.boot.module.system.core.controller.vo.OrganizationReqVO;
import com.onework.boot.module.system.core.dal.dataobject.Organization;
import com.onework.boot.module.system.core.service.IOrganizationService;
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
 * @since 2025-05-26
 */
@RestController
@RequestMapping("/organization")
public class OrganizationController {
    private final IOrganizationService service;

    public OrganizationController(IOrganizationService service) {
        this.service = service;
    }

    @Operation(description = "新增或删除", summary = "新增或删除")
    @PostMapping("addOrUpdate")
    public CommonResult<Organization> addOrUpdate(@RequestBody Organization params) {
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
    public CommonResult<Organization> get(@PathVariable String id) {
        return CommonResult.success(service.getById(id));
    }

    @Operation(description = "查询列表", summary = "查询列表")
    @GetMapping("getAll")
    public CommonResult<List<Organization>> getAll(@Validated OrganizationReqVO reqVO) {
        LambdaQueryWrapperX<Organization> queryWrapper = new LambdaQueryWrapperX<>();
        List<Organization> resultData = service.list(queryWrapper);
        return CommonResult.success(resultData);
    }

    @Operation(description = "分页查询", summary = "分页查询")
    @GetMapping("getPageAll")
    public CommonResult<PageResult<Organization>> getPageList(@Validated OrganizationPageReqVO pageReqVO) {

        Page<Organization> page = MyBatisUtils.buildPage(pageReqVO);
        LambdaQueryWrapperX<Organization> queryWrapper = new LambdaQueryWrapperX<>();
        long total = service.count(queryWrapper);
        List<Organization> data = service.list(page, queryWrapper);
        PageResult<Organization> pageResult = new PageResult<>(data, total);
        return CommonResult.success(pageResult);
    }
}
