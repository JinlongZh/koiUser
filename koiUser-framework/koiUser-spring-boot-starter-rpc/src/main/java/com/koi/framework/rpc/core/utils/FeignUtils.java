package com.koi.framework.rpc.core.utils;

import cn.hutool.core.util.ReflectUtil;
import com.koi.common.utils.json.JsonUtils;
import feign.RequestTemplate;
import feign.template.HeaderTemplate;
import feign.template.Literal;
import feign.template.Template;
import feign.template.TemplateChunk;

import java.util.List;
import java.util.Map;

/**
 * Feign 工具类
 *
 * @Author zjl
 * @Date 2023/8/5 17:20
 */
public class FeignUtils {

    /**
     * 添加 JSON 格式的 Header
     *
     * @param requestTemplate 请求
     * @param name header 名
     * @param value header 值
     */
    @SuppressWarnings("unchecked")
    public static void createJsonHeader(RequestTemplate requestTemplate, String name, Object value) {
        if (value == null) {
            return;
        }
        // 添加 header
        String valueStr = JsonUtils.toJsonString(value);
        requestTemplate.header(name, valueStr);
        // fix：由于 OpenFeign 针对 { 会进行分词，所以需要反射修改
        // 具体分析，可见 https://zhuanlan.zhihu.com/p/360501330 文档
        Map<String, HeaderTemplate> headers = (Map<String, HeaderTemplate>)
                ReflectUtil.getFieldValue(requestTemplate, "headers");
        HeaderTemplate template = headers.get(name);
        List<Template> templateValues = (List<Template>)
                ReflectUtil.getFieldValue(template, "values");
        List<TemplateChunk> templateChunks = (List<TemplateChunk>)
                ReflectUtil.getFieldValue(templateValues.get(0), "templateChunks");
        templateChunks.set(0, Literal.create(valueStr));
    }

}
