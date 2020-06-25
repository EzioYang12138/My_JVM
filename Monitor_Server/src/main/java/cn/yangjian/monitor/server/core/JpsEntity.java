package cn.yangjian.monitor.server.core;

import lombok.Data;

import java.util.List;

//jps(Java Virtual Machine Process Status Tool)是JDK 1.5提供的一个显示当前所有java进程pid的命令，
//linux系统里的ps命令主要是用来显示当前系统的进程情况，有哪些进程，及其 id。
//jps 也是一样，它的作用是显示当前系统的java进程情况，及其id号。我们可以通过它来查看我们到底启动了几个java进程
//（因为每一个java程序都会独占一个java虚拟机实例），和他们的进程号

@Data
public class JpsEntity {
    private String className; //全名
    private String smallName; //小名
    private List<String> parameters; //参数
}
