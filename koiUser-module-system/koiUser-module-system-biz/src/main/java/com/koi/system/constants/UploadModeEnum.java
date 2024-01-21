package com.koi.system.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 上传模式枚举
 *
 * @Author zjl
 * @Date 2024/1/21 17:57
 */
@Getter
@AllArgsConstructor
public enum UploadModeEnum {
    /**
     * oss
     */
    OSS("oss", "ossUploadStrategyImpl"),
    /**
     * 本地
     */
    LOCAL("local", "localUploadStrategyImpl"),

    /**
     * cos
     */
    COS("cos", "cosUploadStrategyImpl"),

    /**
     * 七牛云
     */
    QINIU("qiniu", "qiniuUploadStrategyImpl");

    /**
     * 模式
     */
    private final String mode;

    /**
     * 策略
     */
    private final String strategy;

    /**
     * 获取策略
     *
     * @param mode 模式
     * @return {@link String} 搜索策略
     */
    public static String getStrategy(String mode) {
        for (UploadModeEnum value : UploadModeEnum.values()) {
            if (value.getMode().equals(mode)) {
                return value.getStrategy();
            }
        }
        return null;
    }

}
