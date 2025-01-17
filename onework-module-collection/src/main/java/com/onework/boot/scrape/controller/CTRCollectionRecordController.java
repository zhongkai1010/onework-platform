package com.onework.boot.scrape.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.onework.boot.framework.common.pojo.CommonResult;
import com.onework.boot.framework.common.pojo.PageResult;
import com.onework.boot.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.onework.boot.framework.mybatis.core.util.MyBatisUtils;
import com.onework.boot.scrape.controller.vo.CTRCollectionRecordPageReqVO;
import com.onework.boot.scrape.controller.vo.CTRCollectionRecordReqVO;
import com.onework.boot.scrape.dal.dataobject.CTRCollectionRecord;
import com.onework.boot.scrape.service.CTRCollectionRecordService;
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
@RequestMapping("/scrape/ctr-collection-record")
public class CTRCollectionRecordController {
    private final CTRCollectionRecordService service;

    public CTRCollectionRecordController(CTRCollectionRecordService service) {
        this.service = service;
    }

    @Operation(description = "新增或删除", summary = "新增或删除")
    @PostMapping("addOrUpdate")
    public CommonResult<CTRCollectionRecord> addOrUpdate(@RequestBody CTRCollectionRecord params) {
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
    public CommonResult<CTRCollectionRecord> get(@PathVariable String id) {
        return CommonResult.success(service.getById(id));
    }

    @Operation(description = "查询列表", summary = "查询列表")
    @GetMapping("getAll")
    public CommonResult<List<CTRCollectionRecord>> getAll(@Validated CTRCollectionRecordReqVO reqVO) {
        LambdaQueryWrapperX<CTRCollectionRecord> queryWrapper = new LambdaQueryWrapperX<>();
        List<CTRCollectionRecord> resultData = service.list(queryWrapper);
        return CommonResult.success(resultData);
    }

    @Operation(description = "分页查询", summary = "分页查询")
    @GetMapping("getPageAll")
    public CommonResult<PageResult<CTRCollectionRecord>> getPageList(@Validated CTRCollectionRecordPageReqVO pageReqVO) {

        Page<CTRCollectionRecord> page = MyBatisUtils.buildPage(pageReqVO);
        LambdaQueryWrapperX<CTRCollectionRecord> queryWrapper = new LambdaQueryWrapperX<>();
        long total = service.count(queryWrapper);
        List<CTRCollectionRecord> data = service.list(page, queryWrapper);
        PageResult<CTRCollectionRecord> pageResult = new PageResult<>(data, total);
        return CommonResult.success(pageResult);
    }
}
