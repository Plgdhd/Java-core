package com.plgdhd;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ApplicationContext("com.plgdhd");
        CoronaDesinfector disinfector = context.getBean(CoronaDesinfector.class);
        disinfector.start(new Room());
    }
}
