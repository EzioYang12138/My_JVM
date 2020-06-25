package cn.yangjian.monitor.client.dump.timer;

import cn.yangjian.monitor.client.core.util.PathUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class ClearDumpJob {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    /**
     * 清理快照目录
     */
    @Scheduled(cron = "0 0 * * * ?")
//    秒 分钟 小时 日 月 星期几   星表示每...   比如上边指的是每小时的第0分第0秒触发方法
    public void clearDump() {
        String path = PathUtil.getRootPath("dump/");
        File file = new File(path);
        if (!file.exists()){
            return;
        }
        try {
            FileUtils.deleteDirectory(file);
            logger.warn("Delete dump directory successful");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
