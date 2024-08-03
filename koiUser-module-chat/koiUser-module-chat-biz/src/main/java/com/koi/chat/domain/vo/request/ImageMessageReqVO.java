package com.koi.chat.domain.vo.request;

import com.koi.chat.domain.vo.BaseFileVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 图像消息req-vo
 *
 * @Author zjl
 * @Date 2023/12/10 16:43
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ImageMessageReqVO extends BaseFileVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 宽度（像素）
     */
    @NotNull
    private Integer width;

    /**
     * 高度（像素）
     */
    @NotNull
    private Integer height;

}
