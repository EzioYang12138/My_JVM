package cn.yangjian.monitor.server.timer.config;

import cn.yangjian.monitor.server.timer.job.ClearJob;
import cn.yangjian.monitor.server.timer.job.UpdataJob;
import cn.yangjian.monitor.server.timer.parm.CronParm;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class QuartzConfig {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private CronParm cronParm;

//    创建job，需要做什么事
    @Bean
    public JobDetail clearQuartzDetail() {
        return JobBuilder.newJob(ClearJob.class).withIdentity("clearJob").storeDurably().build();
    }

//    创建trigger，在什么时间做
    @Bean
    public Trigger clearQuartzTrigger() {
        logger.warn("monitor.cron: " + cronParm.getCron());
        return TriggerBuilder.newTrigger().forJob(clearQuartzDetail())
                .withIdentity("clearTrigger") // 定义name/group
                .startNow()
                .withSchedule(
                        //Cron表达式：[秒][分][时][日][月][周][年] (周日1-周六7，年可不写)   *每 ?不关心 -至 #第 /递增 ,和 L最后 W最近工作日
                        CronScheduleBuilder.cronSchedule(cronParm.getCron())
                )
                .build();
    }

    @Bean
    public JobDetail updataQuartzDetail() {
        return JobBuilder.newJob(UpdataJob.class).withIdentity("updataJob").storeDurably().build();
    }

    @Bean
    public Trigger updataQuartzTrigger() {
        logger.warn("monitor.rate: " + cronParm.getRate());
        return TriggerBuilder.newTrigger().forJob(updataQuartzDetail())
                .withIdentity("updataTrigger")
                .startNow()
                .withSchedule(
//                        简单的trigger触发时间，通过quartz提供的方法完成简单的重复调用
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(cronParm.getRate())
                                .repeatForever()
                )
                .build();
    }

}
