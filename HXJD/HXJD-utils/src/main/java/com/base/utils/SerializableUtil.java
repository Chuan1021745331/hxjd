package com.base.utils;

import java.io.ByteArrayInputStream;  
import java.io.ByteArrayOutputStream;  
import java.io.IOException;  
import java.io.ObjectInputStream;  
import java.io.ObjectOutputStream;  

/**
 * 辅助类SerializableUtil，这个类用来把java对象序列化成字节数组，或者把字节数组反序列化成java对象。
 * @author z
 *
 */
public class SerializableUtil {  
      
    public static byte[] toBytes(Object object) {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        ObjectOutputStream oos = null;  
        try {  
            oos = new ObjectOutputStream(baos);  
            oos.writeObject(object);  
            byte[] bytes = baos.toByteArray();  
            return bytes;  
        } catch(IOException ex) {  
            throw new RuntimeException(ex.getMessage(), ex);  
        } finally {  
            try {  
                oos.close();  
            } catch (Exception e) {}  
        }  
    }  
      
    public static Object toObject(byte[] bytes) {  
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);  
        ObjectInputStream ois = null;  
        try {  
            ois = new ObjectInputStream(bais);  
            Object object = ois.readObject();  
            return object;  
        } catch(IOException ex) {  
            throw new RuntimeException(ex.getMessage(), ex);  
        } catch(ClassNotFoundException ex) {  
            throw new RuntimeException(ex.getMessage(), ex);  
        } finally {  
            try {  
                ois.close();  
            } catch (Exception e) {}  
        }  
    }  
}