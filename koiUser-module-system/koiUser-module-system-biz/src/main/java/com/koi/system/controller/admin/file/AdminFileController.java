package com.koi.system.controller.admin.file;

import com.koi.common.domain.CommonResult;
import com.koi.system.constants.FilePathEnum;
import com.koi.system.strategy.context.UploadStrategyContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;

/**
 * 管理后台 - 文件
 *
 * @Author zjl
 * @Date 2024/1/22 17:01
 */
@Tag(name = "管理后台 - 文件")
@RestController
@RequestMapping("/system/file")
public class AdminFileController {

    @Resource
    private UploadStrategyContext uploadStrategyContext;

    @PermitAll
    @Operation(summary = "上传封面图片")
    @PostMapping("/upload-cover")
    public CommonResult<String> uploadCover(MultipartFile file) {
        String path = uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.getPathByDate(FilePathEnum.COVER));
        return CommonResult.success(path);
    }

    @PermitAll
    @Operation(summary = "上传markdown图片")
    @PostMapping("/upload-md")
    public CommonResult<String> uploadMd(MultipartFile file) {
        String path = uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.getPathByDate(FilePathEnum.MD));
        return CommonResult.success(path);
    }

}
