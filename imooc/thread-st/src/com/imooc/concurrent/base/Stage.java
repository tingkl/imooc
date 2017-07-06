package com.imooc.concurrent.base;

/**
 * Created by mishi02 on 2017/7/5.
 */
public class Stage extends Thread {

    @Override
    public void run () {
        ArmyRunnable armyTaskOfSuiDynasty = new ArmyRunnable();
        ArmyRunnable armyTaskOfRevolt = new ArmyRunnable();

        Thread armyOfSuiDynasty = new Thread(armyTaskOfSuiDynasty, "隋军");
        Thread armyOfRevolt = new Thread(armyTaskOfRevolt, "农民起义军");

        armyOfRevolt.start();
        armyOfSuiDynasty.start();

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("正当双方激战正酣，半路杀出个程咬金");

        Thread mrCheng = new KeyPersonThread();
        mrCheng.setName("程咬金");

        // 停止军队作战
        armyTaskOfRevolt.keepRunning = false;
        armyTaskOfSuiDynasty.keepRunning = false;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mrCheng.start();
        try {
            // 万众瞩目，所有现场等待程先生完成历史使命
            // join方法，使其他线程等待自己执行完毕，相当于独占cpu
            mrCheng.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("程咬金结束后，输出此处");
    }

    public static void main(String[] args) {
        new Stage().start();
    }
}
