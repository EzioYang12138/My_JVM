package cn.yangjian.monitor.server.core;

import lombok.Data;

//Stack Trace for Java
//Java堆栈跟踪工具，jstack用于生成虚拟机当前时刻的线程快照（一般称为threaddump或者javacore文件）。
//jstack线程快照就是当前虚拟机内每一条线程正在执行的方法堆栈的集合，生成线程快照的主要目的是定位线程出现长时间停顿的原因，
//比如线程间死锁、死循环、请求外部资源导致的长时间等待等都是其常见原因。

@Data
public class JstackEntity {
    private String id;
    private int total;
    private int RUNNABLE;
    private int TIMED_WAITING;
    private int WAITING;
}
