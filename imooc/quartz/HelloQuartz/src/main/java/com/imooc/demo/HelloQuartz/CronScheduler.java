package com.imooc.demo.HelloQuartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tingkl on 2017/7/4.
 */
public class CronScheduler {

    // Cron表达式
    // 字段    是否必填    允许值               允许的特殊字符
    // 秒       是        0~59                ,-*/
    // 分       是        0~59
    // 小时     是        0~23
    // 日       是        1~31
    // 月       是        1~12或者JAN-DEC      ,-*/
    // 周       是        1~7 或者SUN-SAT      ,-*?/LC#
    // 年       否                             ,-*/

    //# 特殊符号表示 '第'   L 特殊表示 last

    /*
    * 0 0/5 14 * * ?               每天下午的2点到2点59（整点开始， 每隔5分钟触发）
    *
    * 0 15 10 ？ * MON-FRI         从周一到周五每天上午的10点15分触发（年不关心，可以不写）
    *
    * 0 15 10 ？ * 6#3             每月的第三周的星期五开始触发
    *
    * 0 15 10 ？ * 6L 2016-2017     2016-2017年每月最后一周的星期五
    * */
    public static void main(String[] args) throws SchedulerException, InterruptedException {
        // 创建一个jobDetail实例，将改实例与HelloJobClass绑定
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("myJob", "detail-group1")
                .usingJobData("message", "hello myJob1")
                .usingJobData("floatJobValue", new Float(3.1415926))
                .build();

        System.out.println("jobDetail name:" + jobDetail.getKey().getName());
        System.out.println("jobDetail group:" + jobDetail.getKey().getGroup());
        System.out.println("jobDetail jobClass:" + jobDetail.getJobClass().getName());
        // 创建一个Trigger实例,定义改job立即执行，每两秒重复执行一次

        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger", "trigger-group1")
                .usingJobData("myTrigger", "group1")
                .usingJobData("message", "hello myTrigger1")
                .startNow()
                .withSchedule(
                        // 每天的14点到15点 还有 18点到19点期间的每5秒钟，执行一次
                        // CronScheduleBuilder.cronSchedule("0/5 * 14,18 * * ?")
                        CronScheduleBuilder.cronSchedule("* * * * * ?")
                )
                .build();

        // 创建Scheduler实例
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler  scheduler = schedulerFactory.getScheduler();
        scheduler.start();
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Current Time Is:" + sf.format(date));
        // 最近的一次执行的时间
        Date executeDate = scheduler.scheduleJob(jobDetail, trigger);
        System.out.println("最近的一次执行的时间:" + sf.format(executeDate));

        // scheduler执行两秒后挂起
        Thread.sleep(2000l);
        scheduler.standby();
        System.out.println("挂起:" + sf.format(new Date()));
        // scheduler挂起3秒后重启
        Thread.sleep(4000l);
        System.out.println("重启:" + sf.format(new Date()));
        scheduler.start();
    }
}
