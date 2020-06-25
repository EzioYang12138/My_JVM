package cn.yangjian.monitor.client.system;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SystemController {

    @RequestMapping("/system")
    public Map<String, String> getSystemMessage(){
        Map<String,String> map = new HashMap<>();
        OperatingSystemMXBean operatingSystem = ManagementFactory.getOperatingSystemMXBean();
        //系统名称
        map.put("Name",operatingSystem.getName());
        //位数
        map.put("Arch",operatingSystem.getArch());
        //处理器数
        map.put("AvailableProcessors",String.valueOf(operatingSystem.getAvailableProcessors()));
        //操作系统版本
        map.put("Version",operatingSystem.getVersion());
        return map;
    }
}
