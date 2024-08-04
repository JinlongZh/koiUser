package com.koi.chat.strategy.msg.factory;

import cn.hutool.core.util.ObjectUtil;
import com.koi.chat.strategy.msg.AbstractMessageHandler;
import com.koi.common.exception.ServiceException;

import java.util.HashMap;
import java.util.Map;

import static com.koi.common.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST;

/**
 * 描述
 *
 * @Author zjl
 * @Date 2023/10/8 23:12
 */
public class MessageHandlerFactory {

    private static final Map<Integer, AbstractMessageHandler> STRATEGY_MAP = new HashMap<>();

    public static void register(Integer code, AbstractMessageHandler strategy) {
        STRATEGY_MAP.put(code, strategy);
    }

    public static AbstractMessageHandler getStrategyNoNull(Integer code) {
        AbstractMessageHandler strategy = STRATEGY_MAP.get(code);
        if (ObjectUtil.isEmpty(strategy)) {
            throw new ServiceException(BAD_REQUEST.getCode(), "参数校验失败");
        }
        return strategy;
    }

}
