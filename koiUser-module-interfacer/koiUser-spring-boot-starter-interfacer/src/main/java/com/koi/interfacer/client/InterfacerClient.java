package com.koi.interfacer.client;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.koi.interfacer.framework.config.KoiUserInterfacerProperties;
import com.koi.interfacer.framework.exception.InterfacerClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;

import java.io.IOException;
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

    private final KoiUserInterfacerProperties properties;

    public String invokeInterface(String params, String host, String url, HttpMethod method) throws InterfacerClientException {
        HttpRequest request = createHttpRequest(host, url, method);

        try {
            addRequestHeaders(request, params, method);
            HttpResponse httpResponse = request
                    .header("Accept-Charset", CharsetUtil.UTF_8)
                    .timeout(properties.getTimeout())
                    .execute();
            return JSONUtil.formatJsonStr(httpResponse.body());
        } catch (IOException e) {
            throw new InterfacerClientException("Failed to invoke interface", e);
        }
    }

    private HttpRequest createHttpRequest(String host, String url, HttpMethod method) {
        String fullUrl = host + url;

        switch (method) {
            case GET:
                return HttpRequest.get(fullUrl);
            case POST:
                return HttpRequest.post(fullUrl);
            case PUT:
                return HttpRequest.put(fullUrl);
            case DELETE:
                return HttpRequest.delete(fullUrl);
            default:
                throw new IllegalArgumentException("Unsupported method: " + method);
        }
    }

    private void addRequestHeaders(HttpRequest request, String params, HttpMethod method) throws UnsupportedEncodingException {
        Map<String, String> headers = generateRequestHeaders(params, method.name());
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            request.header(entry.getKey(), entry.getValue());
        }
    }

    private Map<String, String> generateRequestHeaders(String body, String method) throws UnsupportedEncodingException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("accessKey", properties.getAccessKey());
        headers.put("nonce", RandomUtil.randomNumbers(10));
        headers.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        headers.put("sign", genSign(body, properties.getSecretKey()));
        body = URLUtil.encode(body, CharsetUtil.CHARSET_UTF_8);
        headers.put("body", body);
        headers.put("method", method);
        return headers;
    }

}
