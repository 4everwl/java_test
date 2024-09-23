package top.foreverwl.study.test;

import java.lang.reflect.Constructor;

public class Test3 {
    public static void main(String[] args) throws Exception {

        Class clazz = Class.forName("java.lang.Runtime");
        Constructor m = clazz.getDeclaredConstructor();
        m.setAccessible(true);
        clazz.getMethod("exec", String.class).invoke(m.newInstance(), "calc.exe");

    }
}
