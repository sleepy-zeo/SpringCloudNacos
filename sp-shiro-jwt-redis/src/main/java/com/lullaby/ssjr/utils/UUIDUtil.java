package com.lullaby.ssjr.utils;

import java.util.UUID;

public class UUIDUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static int uuidLength() {
        return 32;
    }
}
