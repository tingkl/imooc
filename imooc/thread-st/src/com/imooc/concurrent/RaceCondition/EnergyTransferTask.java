package com.imooc.concurrent.RaceCondition;

/**
 * Created by mishi02 on 2017/7/6.
 */
public class EnergyTransferTask implements Runnable {
    private EnergySystem energySystem;

    private int fromBox;
    private double maxAmount;

    private int DELAY = 10;

    public EnergyTransferTask(EnergySystem energySystem, int from, double max) {
        this.energySystem = energySystem;
        this.fromBox = from;
        this.maxAmount = max;
    }
    @Override
    public void run() {
        try {
            while (true) {
                int toBox = (int) (energySystem.getBoxAmount() * Math.random());
                double amount = maxAmount * Math.random();
                energySystem.transfer(fromBox, toBox, amount);
                Thread.sleep((long) (DELAY * Math.random()));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
