package com.company;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Class<?> cls = TextContainer.class;
        if (cls.isAnnotationPresent(SaveTo.class)) {
            String path = cls.getAnnotation(SaveTo.class).path();
            Method[] methods = cls.getDeclaredMethods();
            for (Method el : methods) {
                if (el.isAnnotationPresent(Saver.class)) {
                    el.invoke(new TextContainer(), path);
                }
            }
        }
    }
}
