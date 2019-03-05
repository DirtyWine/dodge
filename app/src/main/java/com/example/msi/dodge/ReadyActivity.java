package com.example.msi.dodge;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.example.msi.dodge.Thread.ClientThread;

import java.net.Socket;

public class ReadyActivity extends BaseActivity {

    private final String ip = "";
    private final int port = 0;
    private String user;
    private Handler handler;
    private ClientThread clientThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                String msg = message.obj.toString();
                if (true)
                {

                }
                return true;
            }
        });

        //创建客户端网络通信子线程
        clientThread = new ClientThread(handler,ip,port,user);
        new Thread(clientThread).start();
    }



}
