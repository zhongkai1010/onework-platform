package com.onework.boot.module.crawler.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.onework.boot.framework.common.pojo.CommonResult;
import com.onework.boot.framework.common.pojo.PageResult;
import com.onework.boot.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.onework.boot.framework.mybatis.core.util.MyBatisUtils;
import com.onework.boot.module.crawler.controller.admin.vo.BOHECompanyPageReqVO;
import com.onework.boot.module.crawler.controller.admin.vo.BOHECompanyReqVO;
import com.onework.boot.module.crawler.dal.dataobject.BOHECompany;
import com.onework.boot.module.crawler.service.BOHECompanyService;
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
 * @since 2025-05-30
 */
@RestController
@RequestMapping("/bohe-company")
public class BOHECompanyController {
    private final BOHECompanyService service;

    public BOHECompanyController(BOHECompanyService service) {
        this.service = service;
    }

    @Operation(description = "新增或删除", summary = "新增或删除")
    @PostMapping("addOrUpdate")
    public CommonResult<BOHECompany> addOrUpdate(@RequestBody BOHECompany params) {
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
    public CommonResult<BOHECompany> get(@PathVariable String id) {
        return CommonResult.success(service.getById(id));
    }

    @Operation(description = "查询列表", summary = "查询列表")
    @GetMapping("getAll")
    public CommonResult<List<BOHECompany>> getAll(@Validated BOHECompanyReqVO reqVO) {
        LambdaQueryWrapperX<BOHECompany> queryWrapper = new LambdaQueryWrapperX<>();
        List<BOHECompany> resultData = service.list(queryWrapper);
        return CommonResult.success(resultData);
    }

    @Operation(description = "分页查询", summary = "分页查询")
    @GetMapping("getPageAll")
    public CommonResult<PageResult<BOHECompany>> getPageList(@Validated BOHECompanyPageReqVO pageReqVO) {

        Page<BOHECompany> page = MyBatisUtils.buildPage(pageReqVO);
        LambdaQueryWrapperX<BOHECompany> queryWrapper = new LambdaQueryWrapperX<>();
        long total = service.count(queryWrapper);
        List<BOHECompany> data = service.list(page, queryWrapper);
        PageResult<BOHECompany> pageResult = new PageResult<>(data, total);
        return CommonResult.success(pageResult);
    }
}
