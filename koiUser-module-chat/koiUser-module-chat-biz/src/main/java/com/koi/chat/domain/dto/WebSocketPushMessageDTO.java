package com.koi.chat.domain.dto;

import com.koi.chat.enums.WebSocketPushTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * websocket推送消息 dto
 *
 * @Author zjl
 * @Date 2023/10/13 18:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketPushMessageDTO implements Serializable {

    /**
     * 推送的ws消息
     */
    private WSBaseRespDTO<?> wsBaseMsg;
    /**
     * 推送的uid
     */
    private List<Long> userIdList;

    /**
     * 推送类型 1个人 2全员
     *
     */
    private Integer pushType;

    public WebSocketPushMessageDTO(Long uid, WSBaseRespDTO<?> wsBaseMsg) {
        this.userIdList = Collections.singletonList(uid);
        this.wsBaseMsg = wsBaseMsg;
        this.pushType = WebSocketPushTypeEnum.USER.getType();
    }

    public WebSocketPushMessageDTO(List<Long> uidList, WSBaseRespDTO<?> wsBaseMsg) {
        this.userIdList = uidList;
        this.wsBaseMsg = wsBaseMsg;
        this.pushType = WebSocketPushTypeEnum.USER.getType();
    }

    public WebSocketPushMessageDTO(WSBaseRespDTO<?> wsBaseMsg) {
        this.wsBaseMsg = wsBaseMsg;
        this.pushType = WebSocketPushTypeEnum.ALL.getType();
    }

}
