package cn.yangjian.jvm;

public class Terminal {

    /**
     * -Xjre /Users/yangjian/Desktop/My_Project/My_JVM
     */
    public static String Xjre;

    public static String userClassPath;
    public static String curClassName;
    public static String curClassNamePath;

    public final static String XJRE = "-Xjre";
    public final static String CP = "-cp";

    public final static Integer HEAP_MAX_SIZE = 4 * 8000;

    public final static String UNIX_SLASH = "/";

    public static void processTerminal(String[] args) {

        /* -Xjre /Users/yangjian/Desktop/My_Project/My_JVM
         */
        int len = args.length;
        for (int i = 0; i < len; i++) {
            switch (args[i]) {
                case Terminal.XJRE: {
                    i++;
                    Terminal.Xjre = args[i];
                    Xjre = processOsSlash(Xjre);
                }
                break;
                case Terminal.CP: {
                    i++;
                    Terminal.userClassPath = args[i];
                    userClassPath = processOsSlash(userClassPath);
                }
                break;
                default:
            }
        }

        curClassName = args[len - 1];

        curClassNamePath = userClassPath + curClassName.replace(".", UNIX_SLASH) + ".class";
        curClassName = curClassName.replace(".", UNIX_SLASH);

    }

    private static String processOsSlash(String path) {
        if (path == null || path.length() == 0) {
            return "";
        }
//        if (!path.endsWith(UNIX_SLASH)) {
//            path += UNIX_SLASH;
//        }

        return path;
    }

    public static String processPath(String path) {
        if(path == null || path.isEmpty()){
            return null;
        }
        if(path.endsWith("class")){
            return path;
        }

        path = curClassName + ".class";

        return path;
    }


}
