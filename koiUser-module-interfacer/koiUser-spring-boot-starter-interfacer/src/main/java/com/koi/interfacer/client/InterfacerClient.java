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
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.koi.common.utils.sign.SignUtils.genSign;
import static com.koi.interfacer.framework.utils.ParamsUtils.convertParamsToMap;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/8/29 16:27
 */
@RequiredArgsConstructor
public class InterfacerClient {

    private final KoiUserInterfacerProperties properties;


    /**
     * 执行方法的调用
     *
     * @param params 参数 格式为: key1=value1&key2=value2&key3=value3
     * @param host   主机名
     * @param url    请求地址
     * @param method 请求方法
     * @Return String 调用返回结果
     */
    public String invokeInterface(String params, String host, String url, HttpMethod method) throws InterfacerClientException {
        Map<String, String> paramsMap = convertParamsToMap(params);
        HttpRequest request = createHttpRequest(host, url, method);

        try {
            addRequestHeaders(request, params, method);
            // 如果是GET请求, 将参数拼接上URL
            if (method == HttpMethod.GET) {
                addQueryParams(request, paramsMap);
            } else {
                addRequestBody(request, paramsMap);
            }
            HttpResponse httpResponse = request
                    .header("Accept-Charset", CharsetUtil.UTF_8)
                    .timeout(properties.getTimeout())
                    .execute();
            return httpResponse.body();
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

    /**
     * 添加请求头
     *
     * @param request
     * @param params
     * @param method
     * @Return void
     */
    private void addRequestHeaders(HttpRequest request, String params, HttpMethod method) throws UnsupportedEncodingException {
        Map<String, String> headers = generateRequestHeaders(method.name(), params);
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            request.header(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 生成请求头
     *
     * @param method
     * @param params
     * @Return Map<String, String>
     */
    private Map<String, String> generateRequestHeaders(String method, String params) throws UnsupportedEncodingException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("accessKey", properties.getAccessKey());
        headers.put("nonce", RandomUtil.randomNumbers(10));
        headers.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        headers.put("sign", genSign(params, properties.getSecretKey()));
        headers.put("params", URLUtil.encode(params, CharsetUtil.CHARSET_UTF_8));
        headers.put("method", method);
        return headers;
    }

    /**
     * 设置 GET 请求时的参数
     *
     * @param request
     * @param params
     * @Return void
     */
    private void addQueryParams(HttpRequest request, Map<String, String> params) {
        if (params != null) {
            StringBuilder queryString = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (queryString.length() > 0) {
                    queryString.append("&");
                }
                queryString.append(key).append("=").append(value);
            }
            request.setUrl(request.getUrl() + "?" + URLUtil.encode(queryString.toString(), StandardCharsets.UTF_8));
        }
    }

    private void addRequestBody(HttpRequest request, Map<String, String> params) {
        if (params != null) {
            request.body(JSONUtil.toJsonStr(params));
            request.contentType("application/json");
        }
    }

}
