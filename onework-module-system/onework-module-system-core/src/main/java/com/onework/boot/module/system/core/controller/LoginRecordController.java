package com.onework.boot.module.system.core.controller;

import com.onework.boot.module.system.core.controller.vo.LoginRecordPageReqVO;
import com.onework.boot.module.system.core.controller.vo.LoginRecordReqVO;
import com.onework.boot.module.system.core.dal.dataobject.LoginRecord;
import com.onework.boot.module.system.core.service.ILoginRecordService;
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
@RequestMapping("/login-record")
public class LoginRecordController {
    private final ILoginRecordService service;

    public LoginRecordController(ILoginRecordService service) {
        this.service = service;
    }

    @Operation(description = "新增或删除", summary = "新增或删除")
    @PostMapping("addOrUpdate")
    public CommonResult<LoginRecord> addOrUpdate(@RequestBody LoginRecord params) {
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
    public CommonResult<LoginRecord> get(@PathVariable String id) {
        return CommonResult.success(service.getById(id));
    }

    @Operation(description = "查询列表", summary = "查询列表")
    @GetMapping("getAll")
    public CommonResult<List<LoginRecord>> getAll(@Validated LoginRecordReqVO reqVO) {
        LambdaQueryWrapperX<LoginRecord> queryWrapper = new LambdaQueryWrapperX<>();
        List<LoginRecord> resultData = service.list(queryWrapper);
        return CommonResult.success(resultData);
    }

    @Operation(description = "分页查询", summary = "分页查询")
    @GetMapping("getPageAll")
    public CommonResult<PageResult<LoginRecord>> getPageList(@Validated LoginRecordPageReqVO pageReqVO) {

        Page<LoginRecord> page = MyBatisUtils.buildPage(pageReqVO);
        LambdaQueryWrapperX<LoginRecord> queryWrapper = new LambdaQueryWrapperX<>();
        long total = service.count(queryWrapper);
        List<LoginRecord> data = service.list(page, queryWrapper);
        PageResult<LoginRecord> pageResult = new PageResult<>(data, total);
        return CommonResult.success(pageResult);
    }
}
