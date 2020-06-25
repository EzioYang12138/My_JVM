package cn.yangjian.monitor.server.remote.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 封装HttpURLConnection开箱即用
 */
public class HttpUtil {
    private final HttpURLConnection connection;

    /**
     * 入口
     */
    public static HttpUtil connect(String url) throws IOException {
        return new HttpUtil((HttpURLConnection) new URL(url).openConnection());
    }

    private HttpUtil(HttpURLConnection connection) {
        this.connection = connection;
    }


    /**
     * 发起请求
     */
    public HttpUtil execute() throws IOException {

        //设置读去超时时间为32秒
        int readTimeout = 32000;
        connection.setReadTimeout(readTimeout);
        //设置链接超时为10秒
        int connectTimeout = 10000;
        connection.setConnectTimeout(connectTimeout);
        //设置请求方式,GET,POST
        String method = "GET";
        connection.setRequestMethod(method.toUpperCase());
        //接受输入流
        boolean doInput = true;
        connection.setDoInput(doInput);
        //不使用缓存
        connection.setUseCaches(false);
        //设置USER-AGENT
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36";
        connection.setRequestProperty("User-Agent", userAgent);
        //发起请求
        connection.connect();
        return this;
    }

    /**
     * 获取响应字符串
     */
    public String getBody(String... charsets) {
        String charset = "UTF-8";
        if (charsets.length > 0) {
            charset = charsets[0];
        }
        try {
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset));
            String line = bufferedReader.readLine();
            StringBuilder builder = new StringBuilder();
            while (line != null) {
                builder.append(line);
                line = bufferedReader.readLine();
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
