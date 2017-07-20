package com.imooc.timer;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;

/**
 * Created by tingkl on 2017/7/17.
 */
public class MyTimer {
    public static void main (String args[]) {
//        Timer timer = new Timer();
//        MyTimerTask myTimerTask = new MyTimerTask("No.1");
//
//        timer.schedule(myTimerTask, 2000l, 1000l);
        test2();

    }


    /*
    * 1. schedule(task, Date)  在时间等于或超过time的时候执行且仅执行一次task
    *
    * */
    public static void test1() {
        Timer timer = new Timer();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println(sf.format(calendar.getTime()));
        calendar.add(Calendar.SECOND, 3);



        MyTimerTask myTimerTask = new MyTimerTask("schedule1");

        timer.schedule(myTimerTask, calendar.getTime());
    }

    /*
    * 2. 时间等于或者超过time时首次执行task，之后每隔period毫秒重复执行一次task
    * schedule(task, Date, period)
    * */
    public static void test2() {
        Timer timer = new Timer();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println(sf.format(calendar.getTime()));
        calendar.add(Calendar.SECOND, 3);



        MyTimerTask myTimerTask = new MyTimerTask("schedule2");

        timer.schedule(myTimerTask, calendar.getTime(), 2000l);
    }

    /*
    * 3. 等待delay毫秒后，执行且只执行一次task
    * */
    public static void test3 () {
        Timer timer = new Timer();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println(sf.format(calendar.getTime()));
        calendar.add(Calendar.SECOND, 3);



        MyTimerTask myTimerTask = new MyTimerTask("schedule3");

        timer.schedule(myTimerTask, 2000l);
    }

    /*
    * 4. 等待delay毫秒后首次执行task，之后每隔period毫秒重复执行一次task
    * */
    public static void test4() {
        Timer timer = new Timer();

        MyTimerTask myTimerTask = new MyTimerTask("schedule4");

        timer.schedule(myTimerTask,  3000l, 2000l);
    }
}
