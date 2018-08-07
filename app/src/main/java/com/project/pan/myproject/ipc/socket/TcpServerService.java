package com.project.pan.myproject.ipc.socket;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * @author pan
 */
public class TcpServerService extends Service {
    private static final int PORT = 1086;
    private boolean isServiceDestoryed = false;
    private String[] defaultMsg = new String[]{
            "HELLO",
            "WAHT IS YOUR NAME"
    };

    public TcpServerService() {
    }

    @Override
    public void onCreate() {
        AsyncTask.execute(()-> startTcpServer());
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }

    @Override
    public void onDestroy() {
        isServiceDestoryed = true;
        super.onDestroy();
    }

    /**
     * 开启socket服务
     */
    public void startTcpServer(){
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (!isServiceDestoryed){
            try {
                Socket client = serverSocket.accept();
                AsyncTask.execute(()->responseClient(client));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 回复客户端
     * @param client
     */
    private void responseClient(Socket client) {

        try {
            //用于接受客户端信息
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            //用于向客户端发送消息
            PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()),true);
            out.println("你好啊，索嗨");
            while (!isServiceDestoryed){
                String content = in.readLine();
                Log.e("content from client",content+"");
                if(content == null){
                    //客户端断开连接
                    break;
                }
                int i = new Random().nextInt(defaultMsg.length);
                String msg = defaultMsg[i];
                out.println(msg);
                Log.e("send msg to client",msg+"");
            }
            in.close();
            out.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
