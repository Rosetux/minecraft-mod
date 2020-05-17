package com.example.examplemod.utils;

public class Assert {
    private Assert() {
    }

    public static void notNull(Object o, String msg) {
        isFalse(org.apache.logging.log4j.core.util.Assert.isEmpty(o), msg);
    }

    public static void isFalse(boolean b, String msg) {
        if(b) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void isTrue(boolean b, String msg) {
        if(!b) {
            throw new IllegalArgumentException(msg);
        }
    }
}
