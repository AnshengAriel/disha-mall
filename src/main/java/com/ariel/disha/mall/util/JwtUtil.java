package com.ariel.disha.mall.util;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ariel.disha.mall.consts.TokenVo;

/**
 * @author ariel
 * @apiNote JwtUtil
 * @serial
 */
public final class JwtUtil {

    private static final String HEADER;

    static {
        JSONObject headerJson = new JSONObject()
                .set("alg", "HS256")
                .set("type", "JWT");
        HEADER = Base64Encoder.encodeUrlSafe(headerJson.toString());
    }

    public static String encodeToken(TokenVo vo, String secret) {
        String payload = Base64Encoder.encodeUrlSafe(JSONUtil.toJsonStr(vo));
        String text = HEADER + "." + payload;
        String sign = Base64Encoder.encodeUrlSafe(
                new HMac(HmacAlgorithm.HmacSHA256, secret.getBytes()).digestHex(text));
        return text + "." + sign;
    }

    public static TokenVo decodeToken(String token, String secret) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            return null;
        }
        String text = parts[0] + "." + parts[1];
        String sign = Base64Encoder.encodeUrlSafe(
                new HMac(HmacAlgorithm.HmacSHA256, secret.getBytes()).digestHex(text));
        if (!sign.equals(parts[2])) {
            return null;
        }
        return JSONUtil.toBean(Base64Decoder.decodeStr(parts[1]), TokenVo.class);
    }

    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        String secret = "DatQ2tb1ebKUrfNiZ55s1N4CZ4fM1Xsp";
        TokenVo tokenVo = new TokenVo();
        tokenVo.setAccountType(1);
        tokenVo.setAccountName("sdfgdgf");
        tokenVo.setExpireTime(System.currentTimeMillis() + 10000000);
        tokenVo.setAccountId("1231234534");

        String token = encodeToken(tokenVo, secret);
        System.out.println(token);
        System.out.println(decodeToken(token, secret));
    }

}
