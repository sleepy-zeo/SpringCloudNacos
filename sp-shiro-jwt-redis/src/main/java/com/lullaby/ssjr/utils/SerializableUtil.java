package com.lullaby.ssjr.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializableUtil {

    private SerializableUtil() {
    }

    // Object -> byte[]
    public static byte[] serialize(Object object) throws Exception {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            return baos.toByteArray();
        } finally {
            if (oos != null) {
                oos.close();
            }
            if (baos != null) {
                baos.close();
            }
        }
    }

    // byte[] -> Object
    public static Object deserialize(byte[] bytes) throws Exception {
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } finally {
            if (ois != null) {
                ois.close();
            }
            if (bais != null) {
                bais.close();
            }
        }
    }
}
