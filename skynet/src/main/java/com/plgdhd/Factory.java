package com.plgdhd;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Factory implements Runnable {

    private final List<Parts> storage;
    private final Random rnd = new Random();
    private final AtomicBoolean running;

    public Factory(List<Parts> storage, AtomicBoolean running) {
        this.storage = storage;
        this.running = running;
    }

    @Override
    public void run() {
        for(int day = 0; day<=100; ++day){
            synchronized(storage){
                for(int i = 0; i < 10; ++i){
                    int detailCode = rnd.nextInt(4);
                    storage.add(Parts.getNameByCode(detailCode));
                }

                System.out.println("Day: " + day + " Details on factory: " + storage.size());
            }

            try{
                Thread.sleep(150);
            }
            catch(InterruptedException e){

            }
        }
        running.set(false);
    }

}
