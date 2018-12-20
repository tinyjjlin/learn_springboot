package com.brs.adadmin.jwt;


/**
 * @author tiny lin
 * @date 2018/11/30
 */

public class JwtConstant {
    /**
     * 签名秘钥
     */
    public final static String SECRET = "tiny";

    /**
     * 过期时间  单位分钟
     */
    public final static Integer EXPIRES_MINUTE = 30;
    /**
     * key token
     */
    public final static String TOKEN = "token";

    /**
     * 登录名
     */
    public final static String CLAIM_KEY_LOGIN_NMAE="login_name";

}
