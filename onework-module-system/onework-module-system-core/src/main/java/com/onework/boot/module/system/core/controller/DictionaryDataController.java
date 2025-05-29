package com.onework.boot.module.system.core.controller;

import com.onework.boot.module.system.core.controller.vo.DictionaryDataPageReqVO;
import com.onework.boot.module.system.core.controller.vo.DictionaryDataReqVO;
import com.onework.boot.module.system.core.dal.dataobject.DictionaryData;
import com.onework.boot.module.system.core.service.IDictionaryDataService;
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
@RequestMapping("/dictionary-data")
public class DictionaryDataController {
    private final IDictionaryDataService service;

    public DictionaryDataController(IDictionaryDataService service) {
        this.service = service;
    }

    @Operation(description = "新增或删除", summary = "新增或删除")
    @PostMapping("addOrUpdate")
    public CommonResult<DictionaryData> addOrUpdate(@RequestBody DictionaryData params) {
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
    public CommonResult<DictionaryData> get(@PathVariable String id) {
        return CommonResult.success(service.getById(id));
    }

    @Operation(description = "查询列表", summary = "查询列表")
    @GetMapping("getAll")
    public CommonResult<List<DictionaryData>> getAll(@Validated DictionaryDataReqVO reqVO) {
        LambdaQueryWrapperX<DictionaryData> queryWrapper = new LambdaQueryWrapperX<>();
        List<DictionaryData> resultData = service.list(queryWrapper);
        return CommonResult.success(resultData);
    }

    @Operation(description = "分页查询", summary = "分页查询")
    @GetMapping("getPageAll")
    public CommonResult<PageResult<DictionaryData>> getPageList(@Validated DictionaryDataPageReqVO pageReqVO) {

        Page<DictionaryData> page = MyBatisUtils.buildPage(pageReqVO);
        LambdaQueryWrapperX<DictionaryData> queryWrapper = new LambdaQueryWrapperX<>();
        long total = service.count(queryWrapper);
        List<DictionaryData> data = service.list(page, queryWrapper);
        PageResult<DictionaryData> pageResult = new PageResult<>(data, total);
        return CommonResult.success(pageResult);
    }
}
