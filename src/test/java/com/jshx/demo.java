package com.jshx;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by YAO on 2017/6/14.
 */
public class demo {

    public static String getToken(String appid){

        String result = "";
        BufferedReader in = null;
        try {
            String urlString = "http://localhost:10001/jshx/token?appid=" + appid;
            URL realUrl = new URL(urlString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static String sendRun(String access_token,String param){
        String url = "http://localhost:10001/jshx/run?access_token="+access_token;

        String result = demo.sendPost(url, param);
        System.out.println(result);
        return result;
    }


    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setConnectTimeout(20000);
            conn.setReadTimeout(300000);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.write(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {

        //首先根据分配的appid获取access_token信息
        String tokenStr = demo.getToken("8a8181f65c9f3816015c9f3e0cde0006");
        ObjectMapper mapper = new ObjectMapper();
        Map<String,String> map = mapper.readValue(tokenStr, Map.class);
        String token = map.get("access_token");

        //此处提交的是运行数据（run），后面两个跟这个一样
        String param = "{ \"lift\": [ { \"regcode\": \"30103201041999012322\", \"isonline\": \"1\", \"timestamps\": \"20170518123006\", \"maincontactor\": \"1\", \"runcontactor\": \"1\", \"circuit\": \"1\", \"mode\": \"1\", \"carstatus\": \"1\", \"direction\": \"0\", \"iszone\": \"1\", \"floor\": \"5\", \"doorstatus\": \"1\", \"uplimit\": \"0\", \"downlimit\": \"0\", \"alarm\": \"0\", \"passenger\": \"0\", \"hours\": \"1245.50\", \"times\": \"2590\" }, { \"regcode\": \"31103201042010060019\", \"isonline\": \"1\", \"timestamps\": \"20170518123005\", \"maincontactor\": \"1\", \"runcontactor\": \"1\", \"circuit\": \"1\", \"mode\": \"1\", \"carstatus\": \"1\", \"direction\": \"0\", \"iszone\": \"1\", \"floor\": \"6\", \"doorstatus\": \"1\", \"uplimit\": \"0\", \"downlimit\": \"0\", \"alarm\": \"0\", \"passenger\": \"0\", \"hours\": \"545.40\", \"times\": \"590\" } ] }";

        String result = demo.sendRun(token, param);
        System.out.println("result is " + result);


    }

}
