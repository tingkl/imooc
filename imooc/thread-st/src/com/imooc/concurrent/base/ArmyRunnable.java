package com.imooc.concurrent.base;

/**
 * Created by mishi02 on 2017/7/5.
 */
public class ArmyRunnable implements Runnable {
    volatile boolean keepRunning = true;
    @Override
    public void run() {
        while(keepRunning) {
            for (int i = 0 ; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "进攻对方[" + i +"]");
                // 让出了处理器时间，下次改谁进攻还不一定
                Thread.yield();
            }
        }
        System.out.println(Thread.currentThread().getName() + " 结束了战斗!");
    }
}
