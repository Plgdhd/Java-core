package com.plgdhd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class World implements Runnable {

    private final List<Parts> factoryStorage;
    private final List<Parts> worldPartsStorage = new ArrayList<>();
    private int robotsCount = 0;
    private final AtomicBoolean running;
    private final Random rnd = new Random();

    public World(List<Parts> factoryStorage, AtomicBoolean running) {
        this.factoryStorage = factoryStorage;
        this.running = running;
    }

    private void buildRobots() {
        boolean haveEnoughDetails = true;
        while (haveEnoughDetails) {
            Map<Parts, Long> detailsCount = worldPartsStorage.stream()
                    .collect(Collectors.groupingBy(p -> p, Collectors.counting()));

            if (detailsCount.getOrDefault(Parts.HEAD, 0L) >= 1 &&
                    detailsCount.getOrDefault(Parts.TORSO, 0L) >= 1 &&
                    detailsCount.getOrDefault(Parts.HAND, 0L) >= 2 &&
                    detailsCount.getOrDefault(Parts.FEET, 0L) >= 2) {
                removePart(Parts.HEAD, 1);
                removePart(Parts.TORSO, 1);
                removePart(Parts.HAND, 2);
                removePart(Parts.FEET, 2);
                robotsCount++;
            }
            else{
                haveEnoughDetails = false;
            }
        }
    }

    public void removePart(Parts part, int code) {
        for (int i = 0; i < code; ++i) {
            worldPartsStorage.remove(part);
        }
    }

    public int getRobotsCount() {
        return robotsCount;
    }

    @Override
    public void run() {
        while (running.get() || !factoryStorage.isEmpty()) {
            synchronized (factoryStorage) {
                for (int i = 0; i < 5; ++i) {
                    if (!factoryStorage.isEmpty()) {
                        int partIndex = rnd.nextInt(factoryStorage.size());
                        worldPartsStorage.add(factoryStorage.remove(partIndex));
                    }
                }

            }
            buildRobots();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("World built: " + robotsCount);
    }

}
