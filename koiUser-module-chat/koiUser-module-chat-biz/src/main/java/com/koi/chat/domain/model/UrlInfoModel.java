package com.koi.chat.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * usl信息
 *
 * @Author zjl
 * @Date 2024/11/16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UrlInfoModel {

    /**
     * 标题
     **/
    String title;

    /**
     * 描述
     **/
    String description;

    /**
     * 网站LOGO
     **/
    String image;

}
