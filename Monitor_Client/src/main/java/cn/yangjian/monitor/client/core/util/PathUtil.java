package cn.yangjian.monitor.client.core.util;


import java.io.File;


public class PathUtil {
    //获取项目的根路径
    public final static String classPath;

    static {
        //获取的是classpath路径，适用于读取resources下资源
//        classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        classPath = System.getProperty("user.dir");
    }

    /**
     * 项目根目录
     */
    public static String getRootPath() {
        return RootPath("");
    }

    /**
     * 自定义追加路径
     */
    public static String getRootPath(String u_path) {
        return RootPath("/" + u_path);
    }

    /**
     * 私有处理方法
     */
    private static String RootPath(String u_path) {
        String rootPath = "";

        //linux下
        if ("/".equals(File.separator)) {
            //system.out.println(classPath);
            rootPath = classPath + u_path;
            rootPath = rootPath.replaceAll("\\\\", "/");
        }
        return rootPath;
    }

}
