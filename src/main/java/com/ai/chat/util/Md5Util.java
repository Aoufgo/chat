package com.ai.chat.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

/**
 * @author aoufgo
 */
@Component
public class Md5Util {
    /**
     * 对字符串进行md5加密
     * @param password 字符串
     * @return 加密密码
     */
    public static String md5(String password){
        byte[] b = DigestUtils.md5(password);
        String pass= DigestUtils.md5Hex(b);
        byte[] b2 = DigestUtils.md5(pass);
        return DigestUtils.md5Hex(b2);
    }

}
