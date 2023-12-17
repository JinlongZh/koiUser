package com.koi.blog.domain.vo.response;

import com.koi.blog.domain.dto.HomeListDTO;
import lombok.Data;

/**
 * 主页列表数据返回
 *
 * @Author zjl
 * @Date 2023/12/17 12:45
 */
@Data
public class HomeListRespVO {

    private Integer type;

    private HomeListDTO homeListDTO;

}
