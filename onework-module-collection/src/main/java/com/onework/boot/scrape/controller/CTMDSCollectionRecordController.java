package com.onework.boot.scrape.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.onework.boot.framework.common.pojo.CommonResult;
import com.onework.boot.framework.common.pojo.PageResult;
import com.onework.boot.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.onework.boot.framework.mybatis.core.util.MyBatisUtils;
import com.onework.boot.scrape.controller.vo.CTMDSCollectionRecordPageReqVO;
import com.onework.boot.scrape.controller.vo.CTMDSCollectionRecordReqVO;
import com.onework.boot.scrape.dal.dataobject.CTMDSCollectionRecord;
import com.onework.boot.scrape.service.CTMDSCollectionRecordService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * <p>
 * 临床机构信息表 前端控制器
 * </p>
 *
 * @author onework
 * @since 2025-01-13
 */
@RestController
@RequestMapping("/scrape/ctmds-collection-record")
public class CTMDSCollectionRecordController {
    private final CTMDSCollectionRecordService service;

    public CTMDSCollectionRecordController(CTMDSCollectionRecordService service) {
        this.service = service;
    }

    @Operation(description = "新增或删除", summary = "新增或删除")
    @PostMapping("addOrUpdate")
    public CommonResult<CTMDSCollectionRecord> addOrUpdate(@RequestBody CTMDSCollectionRecord params) {
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
    public CommonResult<CTMDSCollectionRecord> get(@PathVariable String id) {
        return CommonResult.success(service.getById(id));
    }

    @Operation(description = "查询列表", summary = "查询列表")
    @GetMapping("getAll")
    public CommonResult<List<CTMDSCollectionRecord>> getAll(@Validated CTMDSCollectionRecordReqVO reqVO) {
        LambdaQueryWrapperX<CTMDSCollectionRecord> queryWrapper = new LambdaQueryWrapperX<>();
        List<CTMDSCollectionRecord> resultData = service.list(queryWrapper);
        return CommonResult.success(resultData);
    }

    @Operation(description = "分页查询", summary = "分页查询")
    @GetMapping("getPageAll")
    public CommonResult<PageResult<CTMDSCollectionRecord>> getPageList(@Validated CTMDSCollectionRecordPageReqVO pageReqVO) {

        Page<CTMDSCollectionRecord> page = MyBatisUtils.buildPage(pageReqVO);
        LambdaQueryWrapperX<CTMDSCollectionRecord> queryWrapper = new LambdaQueryWrapperX<>();
        long total = service.count(queryWrapper);
        List<CTMDSCollectionRecord> data = service.list(page, queryWrapper);
        PageResult<CTMDSCollectionRecord> pageResult = new PageResult<>(data, total);
        return CommonResult.success(pageResult);
    }
}
