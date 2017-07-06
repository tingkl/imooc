package com.imooc.concurrent.RaceCondition;

/**
 * Created by mishi02 on 2017/7/5.
 */
public class EnergySystem {
    // 能量盒子
    private final double[] energyBoxes;

    // 同步的实现: wait() notify() notifyAll()
    // 同步的方法都属于java的object对象，而非thread对象，都是object对象的成员函数
    private final Object lockObj = new Object();

    public  EnergySystem(int n, double initialEnergy) {
        energyBoxes = new double[n];
        for (int i = 0; i < energyBoxes.length; i++) {
           energyBoxes[i] = initialEnergy;
        }
    }

    /**
     * 能量的转移，从一个盒子到另一个盒子
     * @param from
     * @param to
     * @param amount
     */
    public void transfer(int from, int to, double amount) {
        synchronized (lockObj) {
//            if (energyBoxes[from] < amount) {
//                return;
//            }
            // while循环，保证条件不满足时认为都会被条件阻挡
            // 而不是继续竞争CPU资源
            while (energyBoxes[from] < amount) {
                try {
                    // 条件不满足，讲当前线程放入Wait Set
                    lockObj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName());
            energyBoxes[from] -= amount;
            System.out.printf("从%d转移%10.2f能量到%d", from, amount, to);
            energyBoxes[to] += amount;
            System.out.printf("能量总和:%10.2f%n", getTotalEnergies());
            // 唤醒所有lockObj上等待的线程，可以继续执行了
            lockObj.notifyAll();
        }
    }

    private double getTotalEnergies() {
        double sum = 0;
        for (double amount : energyBoxes) {
            sum += amount;
        }
        return sum;
    }

    public int getBoxAmount() {
        return energyBoxes.length;
    }
}
