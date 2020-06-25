package cn.yangjian.monitor.server.core;

import lombok.Data;

import java.util.List;

//Configuration Info for Java
//Java配置信息工具，能实时地查看和调整虚拟机各项参数。
// 命令格式   jinfo [option] pid
//如果不传入参数，则输出该进程的所有配置信息


@Data
public class JinfoEntity {

    private List<String> noedefault;
    private List<String> commandLine;

}
