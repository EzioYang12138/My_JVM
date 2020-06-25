package cn.yangjian.monitor.server.timer.parm;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix="monitor")
@Data
public class CronParm {
    private String cron;
    private Integer rate;
}
