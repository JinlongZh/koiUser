package com.koi.system.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件上传路径枚举
 *
 * @Author zjl
 * @Date 2024/1/22 17:23
 */
@Getter
@AllArgsConstructor
public enum FilePathEnum {

    /**
     * 封面路径
     */
    COVER("cover/", "封面路径"),

    /**
     * markdown图片路径
     */
    MD("md/", "markdown图片路径"),
    ;

    /**
     * 路径
     */
    private final String path;

    /**
     * 描述
     */
    private final String desc;

    /**
     * 根据日期获取路径
     *
     * @param filePathEnum
     * @Return String
     * @Date 2023/1/16 21:43
     */
    public static String getPathByDate(FilePathEnum filePathEnum) {
        String datePath = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return filePathEnum.getPath() + datePath + "/";
    }

}
