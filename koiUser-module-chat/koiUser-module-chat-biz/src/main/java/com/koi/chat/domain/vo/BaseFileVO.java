package com.koi.chat.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 文件基础信息
 *
 * @Author zjl
 * @Date 2023/12/10 17:12
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseFileVO implements Serializable {

    /**
     * 大小(字节)
     */
    @NotNull
    private Long size;

    /**
     * 下载地址
     */
    @NotBlank
    private String url;

    private static final long serialVersionUID = 1L;
}
