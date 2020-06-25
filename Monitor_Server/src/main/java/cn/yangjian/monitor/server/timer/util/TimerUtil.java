package cn.yangjian.monitor.server.timer.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimerUtil {
    /**
     * 现在时间
     * @return
     */
    public static String now(){
        SimpleDateFormat format = new SimpleDateFormat("MM/dd HH:mm");
        return format.format(new Date());
    }
}
