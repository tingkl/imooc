package com.imooc.demo.HelloQuartz;

import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tingkl on 2017/7/4.
 */
public class CronJob implements Job {
    public void execute(JobExecutionContext jobExecutionContext) {
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Current Exec Time Is:" + sf.format(date));
        JobKey key = jobExecutionContext.getJobDetail().getKey();
        System.out.println("job identity:" + key.getName() + "," + key.getGroup());
        TriggerKey triggerKey = jobExecutionContext.getTrigger().getKey();
        System.out.println("My Trigger name and group are:" + triggerKey.getName() + "," + triggerKey.getGroup());

    }


}
