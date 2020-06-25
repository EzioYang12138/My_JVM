package cn.yangjian.monitor.client.core.order;

import cn.yangjian.monitor.client.core.cmd.ExecuteCmd;
import cn.yangjian.monitor.client.core.entity.JinfoEntity;
import cn.yangjian.monitor.client.core.util.ArrayUtil;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Jinfo {

    //    JVM默认参数与指定参数
    public static JinfoEntity info(String id) {
        String s = ExecuteCmd.execute(new String[]{"jinfo", "-flags", id});

//        System.out.println(s);
        if (!s.contains("successfully")) {
            return null;
        }
        String flags = "flags:";
        String command = "Command line:";
        //默认参数
        String[] noedefault = ArrayUtil.trim(s.substring(s.indexOf(flags) + flags.length(), s.indexOf(command)).split("\\s+"));
        String[] commandLine = null;
        s = s.substring(s.indexOf(command));
        if (!s.equals(command)) {
            commandLine = s.substring(command.length()).split("\\s+");
        }
        commandLine = ArrayUtil.trim(commandLine);
        return new JinfoEntity(Arrays.stream(noedefault).collect(Collectors.toList()), Arrays.stream(commandLine).collect(Collectors.toList()));
    }

}
