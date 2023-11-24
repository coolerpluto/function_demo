package com.fan.clazz;

public class TestGetClass {

    public static void main(String[] args) {
         Person student = new student();
        Class<? extends Person> aClass = student.getClass();
        System.out.println(aClass);
    }
}
class Person{
    private String name;
}

class student extends Person{

}

class woman extends Person{

}