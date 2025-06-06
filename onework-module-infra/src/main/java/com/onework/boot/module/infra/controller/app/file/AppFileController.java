package com.onework.boot.module.infra.controller.app.file;

import cn.hutool.core.io.IoUtil;
import com.onework.boot.framework.common.pojo.CommonResult;
import com.onework.boot.module.infra.controller.admin.file.vo.file.FileCreateReqVO;
import com.onework.boot.module.infra.controller.app.file.vo.AppFileUploadReqVO;
import com.onework.boot.module.infra.service.file.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.onework.boot.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 App - 文件存储")
@RestController
@RequestMapping("/infra/file")
@Validated
@Slf4j
public class AppFileController {

    @Resource
    private FileService fileService;

    @PostMapping("/upload")
    @Operation(summary = "上传文件")
    @PermitAll
    public CommonResult<String> uploadFile(AppFileUploadReqVO uploadReqVO) throws Exception {
        MultipartFile file = uploadReqVO.getFile();
        byte[] content = IoUtil.readBytes(file.getInputStream());
        return success(fileService.createFile(content, file.getOriginalFilename(),
                uploadReqVO.getDirectory(), file.getContentType()));
    }


    @PostMapping("/create")
    @Operation(summary = "创建文件", description = "模式二：前端上传文件：配合 presigned-url 接口，记录上传了上传的文件")
    @PermitAll
    public CommonResult<Long> createFile(@Valid @RequestBody FileCreateReqVO createReqVO) {
        return success(fileService.createFile(createReqVO));
    }

}
