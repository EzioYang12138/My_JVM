package cn.yangjian.monitor.server.timer.job;

import cn.yangjian.monitor.server.database.service.ClassService;
import cn.yangjian.monitor.server.database.service.GcService;
import cn.yangjian.monitor.server.database.service.ThreadService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;


public class ClearJob extends QuartzJobBean {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private GcService gcService;
    @Autowired
    private ClassService classService;
    @Autowired
    private ThreadService threadService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.warn("Clear all data on a regular basis");
        gcService.clearAll();
        classService.clearAll();
        threadService.clearAll();
    }
}
