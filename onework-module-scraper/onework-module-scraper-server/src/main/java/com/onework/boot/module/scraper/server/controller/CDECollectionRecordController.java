package com.onework.boot.module.scraper.server.controller;

import com.onework.boot.module.scraper.core.dal.dataobject.CDECollectionRecord;
import com.onework.boot.module.scraper.core.service.CDECollectionRecordService;
import com.onework.boot.module.scraper.server.controller.vo.CDECollectionRecordPageReqVO;
import com.onework.boot.module.scraper.server.controller.vo.CDECollectionRecordReqVO;
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
@RequestMapping("/cde-collection-record")
public class CDECollectionRecordController {
    private final CDECollectionRecordService service;

    public CDECollectionRecordController(CDECollectionRecordService service) {
        this.service = service;
    }

    @Operation(description = "新增或删除", summary = "新增或删除")
    @PostMapping("addOrUpdate")
    public CommonResult<CDECollectionRecord> addOrUpdate(@RequestBody CDECollectionRecord params) {
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
    public CommonResult<CDECollectionRecord> get(@PathVariable String id) {
        return CommonResult.success(service.getById(id));
    }

    @Operation(description = "查询列表", summary = "查询列表")
    @GetMapping("getAll")
    public CommonResult<List<CDECollectionRecord>> getAll(@Validated CDECollectionRecordReqVO reqVO) {
        LambdaQueryWrapperX<CDECollectionRecord> queryWrapper = new LambdaQueryWrapperX<>();
        List<CDECollectionRecord> resultData = service.list(queryWrapper);
        return CommonResult.success(resultData);
    }

    @Operation(description = "分页查询", summary = "分页查询")
    @GetMapping("getPageAll")
    public CommonResult<PageResult<CDECollectionRecord>> getPageList(@Validated CDECollectionRecordPageReqVO pageReqVO) {

        Page<CDECollectionRecord> page = MyBatisUtils.buildPage(pageReqVO);
        LambdaQueryWrapperX<CDECollectionRecord> queryWrapper = new LambdaQueryWrapperX<>();
        long total = service.count(queryWrapper);
        List<CDECollectionRecord> data = service.list(page, queryWrapper);
        PageResult<CDECollectionRecord> pageResult = new PageResult<>(data, total);
        return CommonResult.success(pageResult);
    }
}
