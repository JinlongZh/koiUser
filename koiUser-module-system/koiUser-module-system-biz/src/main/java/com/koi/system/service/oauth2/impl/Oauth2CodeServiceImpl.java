package com.koi.system.service.oauth2.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.koi.common.exception.ServiceException;
import com.koi.common.utils.date.DateUtils;
import com.koi.system.domain.oauth2.entity.Oauth2Code;
import com.koi.system.mapper.oauth2.Oauth2CodeMapper;
import com.koi.system.service.oauth2.Oauth2CodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static com.koi.common.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST;

/**
 * OAuth2.0 授权码 Service 实现类
 *
 * @Author zjl
 * @Date 2023/7/30 17:30
 */
@Service
public class Oauth2CodeServiceImpl implements Oauth2CodeService {

    @Resource
    private Oauth2CodeMapper oauth2CodeMapper;

    /**
     * 授权码的过期时间，默认 5 分钟
     */
    private static final Integer TIMEOUT = 5 * 60;

    @Override
    public Oauth2Code createAuthorizationCode(Long userId, Integer userType, String clientId, List<String> scopes, String redirectUri, String state) {
        Oauth2Code code = Oauth2Code.builder()
                .code(generateCode())
                .userId(userId)
                .userType(userType)
                .clientId(clientId)
                .scopes(JSON.toJSONString(scopes))
                .expiresTime(LocalDateTime.now().plusSeconds(TIMEOUT))
                .redirectUri(redirectUri)
                .state(state)
                .build();

        oauth2CodeMapper.insert(code);
        return code;
    }

    @Override
    public Oauth2Code consumeAuthorizationCode(String code) {
        Oauth2Code oauth2Code = oauth2CodeMapper.selectOne(new LambdaQueryWrapper<Oauth2Code>()
                .eq(Oauth2Code::getCode, code));
        if (oauth2Code == null) {
            throw new ServiceException(BAD_REQUEST.getCode(), "授权码不存在");
        }
        if (DateUtils.isExpired(oauth2Code.getExpiresTime())) {
            throw new ServiceException(BAD_REQUEST.getCode(), "授权码已过期");
        }
        // TODO 使用过的授权码要删除
//        oauth2CodeMapper.deleteById(oauth2Code.getId());
        return oauth2Code;
    }

    private static String generateCode() {
        return IdUtil.fastSimpleUUID();
    }
}
