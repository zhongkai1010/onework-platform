package com.onework.boot.module.system.core.controller;

import com.onework.boot.module.system.core.controller.vo.MenuPageReqVO;
import com.onework.boot.module.system.core.controller.vo.MenuReqVO;
import com.onework.boot.module.system.core.dal.dataobject.Menu;
import com.onework.boot.module.system.core.service.IMenuService;
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
@RequestMapping("/menu")
public class MenuController {
    private final IMenuService service;

    public MenuController(IMenuService service) {
        this.service = service;
    }

    @Operation(description = "新增或删除", summary = "新增或删除")
    @PostMapping("addOrUpdate")
    public CommonResult<Menu> addOrUpdate(@RequestBody Menu params) {
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
    public CommonResult<Menu> get(@PathVariable String id) {
        return CommonResult.success(service.getById(id));
    }

    @Operation(description = "查询列表", summary = "查询列表")
    @GetMapping("getAll")
    public CommonResult<List<Menu>> getAll(@Validated MenuReqVO reqVO) {
        LambdaQueryWrapperX<Menu> queryWrapper = new LambdaQueryWrapperX<>();
        List<Menu> resultData = service.list(queryWrapper);
        return CommonResult.success(resultData);
    }

    @Operation(description = "分页查询", summary = "分页查询")
    @GetMapping("getPageAll")
    public CommonResult<PageResult<Menu>> getPageList(@Validated MenuPageReqVO pageReqVO) {

        Page<Menu> page = MyBatisUtils.buildPage(pageReqVO);
        LambdaQueryWrapperX<Menu> queryWrapper = new LambdaQueryWrapperX<>();
        long total = service.count(queryWrapper);
        List<Menu> data = service.list(page, queryWrapper);
        PageResult<Menu> pageResult = new PageResult<>(data, total);
        return CommonResult.success(pageResult);
    }
}
