package com.jshx.utils;

import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;

public class TokenUtil {

    public static HashMap<String, Token> tokenMap = new HashMap<String, Token>();

    // 以token为主键，存放deptid
    public static HashMap<String, String> deptidMap = new HashMap<String, String>();

    public static final long life_cycle_time = 7200 * 1000;

    public static final String time_out = "timeout";
    public static final String does_not_exist = "does not exist";

    /**
     * <生成token并存放内存中>
     *
     * @param deptId
     * @return tokenStr
     * @author yaoyujing
     */
    public static String crearteToken(String deptId) {

        // 根据deptid获取之前的token
        Token token_old = tokenMap.get(deptId);
        if (null != token_old) {
            String tokenStr_old = token_old.getToken();
            // 删除以就token为key的deptid
            deptidMap.remove(tokenStr_old);
        }

        String tokenStr = null;
        // 获取当前时间
        Date date = new Date();
        // 获取当前时间的毫秒数
        long time = System.currentTimeMillis();
        // 拼接需要MD5加密的字符串
        String MD5str = deptId + time;
        // 进行MD5加密
        tokenStr = MD5(MD5str);
        // 将获取的token及时间信息存入Token对象中
        Token token = new Token();
        token.setToken(tokenStr);
        token.setTimeMillis(time);
        token.setCreateTime(date);
        // 将token对象以deptId为key存入map中
        tokenMap.put(deptId, token);
        // 将deptId以tokenStr为key存入map中
        deptidMap.put(tokenStr, deptId);

        return tokenStr;
    }

    /**
     * <获取内存中token，并判断token是否过期>
     *
     * @param deptId
     * @return tokenStr
     * @author yaoyujing
     */
    public static String getToken(String deptId) {
        String tokenStr = null;
        long nowTime = System.currentTimeMillis();
        Token token = tokenMap.get(deptId);
        if (null != token) {
            long timeMillis = token.getTimeMillis();

            // 如果时间间隔小于生命周期，则token有效，返回token字符串；
            if (life_cycle_time > (nowTime - timeMillis)) {
                tokenStr = token.getToken();
            }
            // 如果时间间隔大于生命周期，则token无效，返回“timeout”；
            else {
                tokenStr = time_out;
            }
        } else {
            tokenStr = does_not_exist;
        }
        return tokenStr;
    }

    /**
     * <获取内存中token，并判断token是否过期>
     *
     * @return tokenStr
     * @author yaoyujing
     */
    public static String chechToken(String tokenStr) {
        String appid = deptidMap.get(tokenStr);
        String token = getToken(appid);

        if (token.equals(time_out)) {
            return time_out;
        } else if (token.equals(does_not_exist)) {
            return does_not_exist;
        } else if (token.equals(tokenStr)) {
            return appid;
        } else {
            return null;
        }
    }

    /**
     * <MD5加密算法>
     *
     * @param s
     * @return
     * @author yaoyujing
     */
    public final static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
