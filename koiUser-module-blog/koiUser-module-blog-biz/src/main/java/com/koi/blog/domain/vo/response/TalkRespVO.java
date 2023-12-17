package com.koi.blog.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.koi.blog.domain.dto.HomeListDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 说说 resp-vo
 *
 * @Author zjl
 * @Date 2023/12/17 13:05
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TalkRespVO implements HomeListDTO {

    /**
     * 说说id
     */
    private Long id;

    /**
     * 说说内容
     */
    private String content;

    /**
     * 图片
     */
    @JsonIgnore
    private String images;

    /**
     * 图片列表
     */
    private List<String> imageList;

    /**
     * 是否置顶
     */
    private Integer talkTop;

    /**
     * 状态 0公开 1关闭
     */
    private Integer status;

    /**
     * 阅读量
     */
    private Integer viewCount;

    /**
     * 发表时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
