package com.koi.interfacer.framework.core.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;


/**
 * 签名生成工具类
 *
 * @Author zjl
 * @Date 2023/8/29 15:54
 */
public class SignUtils {


    /**
     * 使用私钥加密参数
     *
     * @param body
     * @param secretKey
     * @Return String
     */
    public static String genSign(String body, String secretKey) {
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        String content = body + "." + secretKey;
        return md5.digestHex(content);
    }

}
