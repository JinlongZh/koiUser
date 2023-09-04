package com.koi.interfacer.domain.vo.response;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.koi.interfacer.domain.dto.RequestParamDTO;
import com.koi.interfacer.domain.dto.ResponseParamDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 接口信息 Response VO
 *
 * @Author zjl
 * @Date 2023/8/29 21:20
 */
@Schema(description = "接口信息 response vo")
@Data
public class InterfaceInfoRespVO {

    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 主机名
     */
    private String host;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 请求参数
     */
    private List<RequestParamDTO> requestParamList;

    /**
     * 响应参数
     */
    private List<ResponseParamDTO> responseParamList;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 接口状态（0-关闭，1-开启）
     */
    private Integer status;

    /**
     * 请求类型
     */
    private String method;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
