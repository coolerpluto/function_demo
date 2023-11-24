package com.fan;

import com.sun.istack.internal.Nullable;

public class TestNullable {
    public static void main(String[] args) {
        String a = a();
        System.out.println(a.length());
    }

    public static String a(){
        return null;
    }
}
