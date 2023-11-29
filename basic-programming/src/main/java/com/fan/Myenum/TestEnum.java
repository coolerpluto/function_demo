package com.fan.Myenum;

public class TestEnum{
    public static void main(String[] args) {
//        System.out.println(a.SPRING.ordinal());
//        System.out.println(a.SPRING.getSeason());
        a[] values = a.values();
        for (a b: values){
            System.out.println(b.getSeason()+b.getInfo());
            System.out.println(b.ordinal());
        }
    }
}

enum a {

    SPRING("春天", "暖和"),
    SUMMER("夏天", "暖和"),
    AUTUMN("秋", "暖和"),
    WINTER("冻", "暖和");


    private String season;
    private String info;

    a(String season, String info){
        this.season = season;
        this.info = info;
    }

    public String getSeason() {
        return season;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return "a{" +
                "season='" + season + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
