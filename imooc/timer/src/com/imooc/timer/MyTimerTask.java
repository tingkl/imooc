package com.imooc.timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimerTask;

/**
 * Created by tingkl on 2017/7/17.
 */
public class MyTimerTask extends TimerTask{
    private String name;

    private int count = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyTimerTask(String inputName) {
        name = inputName;
    }
    @Override
    public void run() {
        if (count < 3) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("Current exec time is: " + sf.format(calendar.getTime()));
            System.out.println("Current exec name is: " + name);
        } else {
            cancel();
            System.out.println("任务取消了");
        }
        count++;
    }
}
