package com.example.msi.dodge;

import android.os.Message;

import java.util.List;
import java.util.Map;

/**
 * @Author: Cross
 * @Description:
 * @Date: 2018/5/19
 * @Modified by
 */
public class Controller {

    private final static int GET = 1;
    private final static int POST = 0;




    /**
     * @Description: 获取命令
     */
    public static int handleCommand (String message) {
        return message.charAt(0)-'0';
    }


    /**
     * @Description: 获取内容
     */
    public static String getContent (String message) {
        return message.split("@")[3];
    }

    /**
     * @Description: 格式化一条传输数据
     */
    public  static String createCommand (Message message, String user) {
        return message.what+"@"+user+"@"+message.arg1+"@"+message.obj.toString();
    }

    /**
     * @Description: 获取头像
     */
    public static int getAvatar(String message) {
        return Integer.valueOf(message.split("@")[2]);
    }

    /**
     * @Description: 获取用户名
     */
    public static String getUser (String message) {
        return message.split("@")[1];
    }
}
