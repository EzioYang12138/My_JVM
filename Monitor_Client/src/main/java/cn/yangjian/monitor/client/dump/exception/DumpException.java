package cn.yangjian.monitor.client.dump.exception;


public class DumpException extends RuntimeException {
    private final String pid;

    public DumpException(String pid) {
        this.pid = pid;
    }

    public String getTip() {
        return "生成快照文件失败！进程号："+pid+"<br>"
                +"建议重启应用！" +"<br>";
    }

}
