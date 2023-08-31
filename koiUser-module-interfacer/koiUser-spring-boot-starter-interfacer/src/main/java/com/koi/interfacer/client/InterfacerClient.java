package com.koi.interfacer.client;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.koi.interfacer.framework.config.KoiUserInterfacerProperties;
import lombok.RequiredArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static com.koi.common.utils.sign.SignUtils.genSign;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/8/29 16:27
 */
@RequiredArgsConstructor
public class InterfacerClient {

    private final KoiUserInterfacerProperties koiUserInterfacerProperties;

    /**
     * 生成请求头
     *
     * @param body
     * @param method
     * @Return Map<String,String>
     */
    private Map<String, String> getHeaderMap(String body, String method) throws UnsupportedEncodingException {
        HashMap<String, String> map = new HashMap<>();
        map.put("accessKey", koiUserInterfacerProperties.getAccessKey());
        map.put("nonce", RandomUtil.randomNumbers(10));
        map.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        map.put("sign", genSign(body, koiUserInterfacerProperties.getSecretKey()));
        body = URLUtil.encode(body, CharsetUtil.CHARSET_UTF_8);
        map.put("body", body);
        map.put("method", method);
        return map;
    }

    public String invokeInterface(String params, String host, String url, String method) throws UnsupportedEncodingException {
        HttpResponse httpResponse = HttpRequest.post(host + url)
                .header("Accept-Charset", CharsetUtil.UTF_8)
                .addHeaders(getHeaderMap(params, method))
                .body(params)
                .execute();
        return JSONUtil.formatJsonStr(httpResponse.body());
    }

}
