package com.lullaby.ssjr.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesUtil {

    private static final Properties PROPERTIES = new Properties();

    private PropertiesUtil() {
    }

    public static void readProperties(String fileName) throws IOException {
        InputStream in = null;
        try {
            in = PropertiesUtil.class.getResourceAsStream("/" + fileName);
            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
            PROPERTIES.load(bf);
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }
}
