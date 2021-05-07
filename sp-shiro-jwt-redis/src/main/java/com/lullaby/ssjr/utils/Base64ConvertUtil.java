package com.lullaby.ssjr.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

// Base64工具
public class Base64ConvertUtil {

    private Base64ConvertUtil() {
    }

    public static String encode(String str) {
        byte[] encodeBytes = Base64.getEncoder().encode(str.getBytes(StandardCharsets.UTF_8));
        return new String(encodeBytes);
    }

    public static String decode(String str) {
        byte[] decodeBytes = Base64.getDecoder().decode(str.getBytes(StandardCharsets.UTF_8));
        return new String(decodeBytes);
    }

}
