package com.winnie.util;

import java.io.*;

/**
 * @author yanyq
 * @date 2021年06月07日
 */
public class ObjectUtils<T> {
    public static <T> T deepClone(Object obj,Class<T> clazz) throws IOException, ClassNotFoundException {
        // 序列化
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oss = new ObjectOutputStream(baos);
        oss.writeObject(obj);
        oss.flush();
        // 反序列化
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        T result = (T) ois.readObject();
        return result;
    }
}
