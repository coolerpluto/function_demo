package com.fan.iocSingle;

public class Test {
    private Test test = new Test();

    public Test(){}

    public  Test getTest(){
        return test;
    }

}
class test2{
    public static void main(String[] args) {
//        Test test = Test.getTest();
//        Test test1 = test.getTest();
//        System.out.println(test == test1);

        Test test = new Test();
    }
}
