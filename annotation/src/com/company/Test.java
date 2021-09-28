package com.company;

public class Test {
    @Annotation(a = 2, b = 5)
    public static void count(int a, int b) {
        System.out.println(a * b);
    }
}
