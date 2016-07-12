package jiemi.com.base.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import jiemi.com.base.application.BaseApplication;
import jiemi.com.base.tools.LogUtil;
import jiemi.com.base.tools.NetUtils;
import jiemi.com.base.tools.ThreadPoolManager;
import jiemi.com.base.tools.UIUtils;

public class NotifyService extends Service {
    /**
     * 线程是否启动
     */

    private boolean isStarted = false;


    /**
     * socket对象
     */

    private Socket socket;


    /**
     * 是否接受消息
     */

    private boolean isReceive = false;

    private Timer heartBeatTimer;
    private TimerTask heartBeatTask;


    private long lastTime = 0l;


    private boolean heartFlag = false;

    private String msg;


    /**
     * 连接异常发送消息
     */

    private boolean sendError = false;

    private AcceptThread thread;

    public NotifyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startSocketServer();

        return super.onStartCommand(intent, flags, startId);
    }

    private void startSocketServer() {
        if (NetUtils.isConnected(BaseApplication.getApplication())) {
            thread = new AcceptThread();
            //thread.start();
            ThreadPoolManager.getInstance().execute(thread);
        }
    }

    class AcceptThread extends Thread {
        @Override
        public void run() {
            super.run();
            if (!isStarted) {
                Log.d("socket", "启动socket-----");
                try {
                    socket = new Socket("182.92.131.23", 7094);
                    isStarted = true;
                    isReceive = true;
                    heartFlag = true;

                    socket.setKeepAlive(true);
                    startMsgLooper(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("socket", "socket断开-----");
                }
            }
        }
    }
    /**
     * 轮询访问服务器
     *
     * @param socket socket链接
     */
    private void startMsgLooper(Socket socket) {

        while (isReceive) {
            msg = receiveMsg(socket);
            if ("{\"state\":200,\"type\":\"confirm\"}".equals(msg)) {
                String current = "all";

                sendMsg(setJson(current), socket);
                startLooperHeart();
            }
            else if("heartbeat".equals(msg)){

            }
            else {
                Log.e("json", "获取的数据" + msg + "-----");
            }
        }
    }
    /**
     * 启动心跳针
     */
    private void  startLooperHeart(){
      heartBeatTimer=new Timer();
        heartBeatTask=new TimerTask() {
            @Override
            public void run() {
                Log.e("%%%heart", "心在跳----------------" + heartFlag);
            if(socket!=null&&heartFlag){
                sendMsg("heartbeat",socket);
            }

            }
        };
        heartBeatTimer.schedule(heartBeatTask, 1000, 5000);
    }
    /**
     * 接收服务器返回数据
     *
     * @param socket socket链接
     */
    private String receiveMsg(Socket socket) {
        try {
            InputStream is = socket.getInputStream();
            PushbackInputStream pushbackInputStream = new PushbackInputStream(is);

            return readLine(pushbackInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
    /**
     * 解析服务器返回的输入流
     *
     * @param in 输入流
     */
    private String readLine(PushbackInputStream in) {
        try {
            char buf[] = new char[128];
            int room = buf.length;
            int offset = 0;
            int c;
            loop:
            while (true) {
                switch (c = in.read()) {
                    case -1:
                    case '\n':
                        break loop;
                    case '\r':
                        int c2 = in.read();
                        if ((c2 != '\n') && (c2 != -1))
                            in.unread(c2);
                        break loop;
                    default:
                        if (--room < 0) {
                            char[] lineBuffer = buf;
                            buf = new char[offset + 128];
                            room = buf.length - offset - 1;
                            System.arraycopy(lineBuffer, 0, buf, 0, offset);
                        }
                        buf[offset++] = (char) c;
                        break;
                }
            }
            if ((c == -1) && (offset == 0))
                return null;
            return String.copyValueOf(buf, 0, offset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 向服务器请求的数据
     *
     * @param current 向服务器请求的类型
     */
    private String setJson(String current){
        try {
            JSONObject obj = new JSONObject();
            obj.put("op", "connected");
            obj.put("waiter_id", "384");
            obj.put("shop_id", "82");
            obj.put("return", current);
            return obj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
    /**
     * 向服务器请求
     *
     * @param string 向服务器请求的数据
     * @param client Socket链接
     */
   private void sendMsg(String string, Socket client){
       try {
           OutputStream out=client.getOutputStream();
           out.write(string.getBytes());
           out.flush();
       } catch (IOException e) {
           e.printStackTrace();
           Log.d("exception", "断开连接-------startHeartBeatThread-----%%"
                   + e.getMessage());
       }
   }
}
