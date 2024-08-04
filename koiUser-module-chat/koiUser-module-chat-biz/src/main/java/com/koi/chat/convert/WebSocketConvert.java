package com.koi.chat.convert;

import com.koi.chat.domain.dto.WSBaseRespDTO;
import com.koi.chat.domain.vo.response.MessageRespVO;
import com.koi.chat.enums.WSRespTypeEnum;

/**
 * websocket套接字转换
 *
 * @Author zjl
 * @Date 2023/10/13 18:32
 */
public interface WebSocketConvert {

    static WSBaseRespDTO<MessageRespVO> buildMessageSend(MessageRespVO msgResp) {
        WSBaseRespDTO<MessageRespVO> wsBaseResp = new WSBaseRespDTO<>();
        wsBaseResp.setType(WSRespTypeEnum.MESSAGE.getType());
        wsBaseResp.setData(msgResp);
        return wsBaseResp;
    }

}
