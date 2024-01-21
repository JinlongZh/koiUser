package com.koi.system.strategy.impl;

import com.koi.common.utils.json.JsonUtils;
import com.koi.system.config.QiniuConfigProperties;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

/**
 * 七牛云上传策略
 *
 * @Author zjl
 * @Date 2024/1/21 18:11
 */
@Service("qiniuUploadStrategyImpl")
@Slf4j
public class QiniuUploadStrategyImpl extends AbstractUploadStrategyImpl {

    @Resource
    private QiniuConfigProperties qiniuConfigProperties;

    @Override
    public Boolean exists(String filePath) {
        return false;
    }

    @Override
    public String upload(String path, String fileName, InputStream inputStream) throws IOException {
        Configuration configuration = new Configuration(Region.huanan());
        UploadManager uploadManager = new UploadManager(configuration);
        Auth auth = Auth.create(qiniuConfigProperties.getAccessKey(), qiniuConfigProperties.getSecretKey());
        String uploadToken;
        uploadToken = auth.uploadToken(qiniuConfigProperties.getBucket());

        Response response = uploadManager.put(inputStream, path + fileName, uploadToken, null, null);
        if (response.statusCode != HttpStatus.OK.value()) {
            log.error("上传请求异常,cause: {}", response.error);
        }
        DefaultPutRet defaultPutRet = JsonUtils.parseObject(response.bodyString(), DefaultPutRet.class);
        // 返回文件路径
        return defaultPutRet.key;
    }

    @Override
    public String getFileAccessUrl(String filePath) {
        return qiniuConfigProperties.getUrl() + filePath;
    }

}
