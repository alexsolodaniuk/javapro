package com.company;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Class cls = Test.class;
        Method[] methods = cls.getDeclaredMethods();
        for (Method elem : methods) {
            if (elem.isAnnotationPresent(Annotation.class)) {
                Annotation res = elem.getAnnotation(Annotation.class);
                elem.invoke(null, res.a(), res.b());
            }
        }
    }
}
