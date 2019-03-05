package com.example.msi.dodge.Thread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.example.msi.dodge.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;


/**
 * @Author: Cross
 * @Description:
 * @Date: 2018/6/18
 * @Modified by
 */
public class ClientThread implements Runnable {

    //命令标识
    private final int LOGIN = 1;
    private final int EXIT = 0;
    private final int MSG = 2;
    private final int IMG = 4;

    private Socket socket;
    private Handler handler;
    public Handler sendHandler;

    //用户信息
    private String ip;
    private int port;
    private String user;

    BufferedReader br = null;
    OutputStream os = null;

    public ClientThread(Handler handler, String ip, int port, String user) {
        this.handler = handler;
        this.ip = ip;
        this.user = user;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(ip,port);
            br = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()
            ));
            os = socket.getOutputStream();
            new Thread () {
                @Override
                public void run() {
                    String content = null;
                    try {
                        //监听并接受消息
                        while ((content = br.readLine()) != null) {
                            int command = Controller.handleCommand(content);
                            Message message = Message.obtain();
                            message.what = command;
                            message.obj = content;
                            handler.sendMessage(message);
                        }
                    } catch ( IOException e ) {
                        e.printStackTrace();
                    }
                }
            }.start();

            Looper.prepare();
            sendHandler = new Handler(new Handler.Callback() {
                //客户端发送数据
                @Override
                public boolean handleMessage(Message message) {
                    try {
                    if (message.what == MSG||message.what ==IMG||message.what ==EXIT) {
                        os.write((Controller.createCommand(message,user) +"\r\n")
                                .getBytes("utf-8"));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
            });
            Looper.loop();

        }
        catch (SocketTimeoutException e1) {
            System.out.println("time out");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
