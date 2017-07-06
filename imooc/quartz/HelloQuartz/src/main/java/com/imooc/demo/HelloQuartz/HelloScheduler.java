package com.imooc.demo.HelloQuartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tingkl on 2017/7/4.
 */
public class HelloScheduler {
    public static void main(String[] args) throws SchedulerException {
        // 创建一个jobDetail实例，将改实例与HelloJobClass绑定
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("myJob", "detail-group1")
                .usingJobData("message", "hello myJob1")
                .usingJobData("floatJobValue", new Float(3.1415926))
                .build();

        System.out.println("jobDetail name:" + jobDetail.getKey().getName());
        System.out.println("jobDetail group:" + jobDetail.getKey().getGroup());
        System.out.println("jobDetail jobClass:" + jobDetail.getJobClass().getName());
        // 创建一个Trigger实例,定义改job立即执行，每两秒重复执行一次

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger", "trigger-group1")
                .usingJobData("myTrigger", "group1")
                .usingJobData("message", "hello myTrigger1")
                .startNow()
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInMilliseconds(2000)
                                .repeatForever())
                .build();

        // 创建Scheduler实例
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler  scheduler = schedulerFactory.getScheduler();
        scheduler.start();
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Current Time Is:" + sf.format(date));
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
