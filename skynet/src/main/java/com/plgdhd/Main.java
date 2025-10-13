package com.plgdhd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<Parts> factoryStorage = Collections.synchronizedList(new ArrayList<>());
        AtomicBoolean running = new AtomicBoolean(true);
        Factory factory = new Factory(factoryStorage, running);
        World world = new World(factoryStorage, running);
        Wednesday wednesday = new Wednesday( factoryStorage, running);

        Thread factoryThread = new Thread(factory);
        Thread t1 = new Thread(world);
        Thread t2 = new Thread(wednesday);

        factoryThread.start();
        t1.start();
        t2.start();

        factoryThread.join();
        t1.join();
        t2.join();

        System.out.printf("World: %d robots%n", world.getRobotsCount());
        System.out.printf("Wednesday: %d robots%n", wednesday.getRobotsCount());
        System.out.println(world.getRobotsCount() > wednesday.getRobotsCount()
                ? "Winner: World" : "Winner: Wednesday");
    }
}