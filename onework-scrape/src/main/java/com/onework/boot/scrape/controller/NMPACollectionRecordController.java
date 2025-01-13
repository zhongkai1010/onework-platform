package com.onework.boot.scrape.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.onework.boot.framework.common.pojo.CommonResult;
import com.onework.boot.framework.common.pojo.PageResult;
import com.onework.boot.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.onework.boot.framework.mybatis.core.util.MyBatisUtils;
import com.onework.boot.scrape.controller.vo.NMPACollectionRecordPageReqVO;
import com.onework.boot.scrape.controller.vo.NMPACollectionRecordReqVO;
import com.onework.boot.scrape.dal.dataobject.NMPACollectionRecord;
import com.onework.boot.scrape.service.NMPACollectionRecordService;
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
@RequestMapping("/scrape/nmpa-collection-record")
public class NMPACollectionRecordController {
    private final NMPACollectionRecordService service;

    public NMPACollectionRecordController(NMPACollectionRecordService service) {
        this.service = service;
    }

    @Operation(description = "新增或删除", summary = "新增或删除")
    @PostMapping("addOrUpdate")
    public CommonResult<NMPACollectionRecord> addOrUpdate(@RequestBody NMPACollectionRecord params) {
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
    public CommonResult<NMPACollectionRecord> get(@PathVariable String id) {
        return CommonResult.success(service.getById(id));
    }

    @Operation(description = "查询列表", summary = "查询列表")
    @GetMapping("getAll")
    public CommonResult<List<NMPACollectionRecord>> getAll(@Validated NMPACollectionRecordReqVO reqVO) {
        LambdaQueryWrapperX<NMPACollectionRecord> queryWrapper = new LambdaQueryWrapperX<>();
        List<NMPACollectionRecord> resultData = service.list(queryWrapper);
        return CommonResult.success(resultData);
    }

    @Operation(description = "分页查询", summary = "分页查询")
    @GetMapping("getPageAll")
    public CommonResult<PageResult<NMPACollectionRecord>> getPageList(@Validated NMPACollectionRecordPageReqVO pageReqVO) {

        Page<NMPACollectionRecord> page = MyBatisUtils.buildPage(pageReqVO);
        LambdaQueryWrapperX<NMPACollectionRecord> queryWrapper = new LambdaQueryWrapperX<>();
        long total = service.count(queryWrapper);
        List<NMPACollectionRecord> data = service.list(page, queryWrapper);
        PageResult<NMPACollectionRecord> pageResult = new PageResult<>(data, total);
        return CommonResult.success(pageResult);
    }
}
