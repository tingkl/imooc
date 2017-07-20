package com.imooc.demo.HelloQuartz;

import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tingkl on 2017/7/4.
 */
public class HelloJob implements Job {
    public void execute(JobExecutionContext jobExecutionContext) {
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Current Exec Time Is:" + sf.format(date));
        JobKey key = jobExecutionContext.getJobDetail().getKey();
        // System.out.println("job identity:" + key.getName() + "," + key.getGroup());
        TriggerKey triggerKey = jobExecutionContext.getTrigger().getKey();
        // System.out.println("My Trigger name and group are:" + triggerKey.getName() + "," + triggerKey.getGroup());

        try {
            JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
            JobDataMap tDataMap = jobExecutionContext.getTrigger().getJobDataMap();

            String jobMsg = dataMap.getString("message");
            Float jobFloatValue = dataMap.getFloatValue("floatJobValue");

            String triggerMsg = tDataMap.getString("message");
            // System.out.println("JobMsg is:" + jobMsg);
            // System.out.println("JobFloatValue is:" + jobFloatValue);

            // System.out.println("triggerMsg is: " + triggerMsg);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


}
