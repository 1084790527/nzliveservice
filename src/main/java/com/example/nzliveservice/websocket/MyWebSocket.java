package com.example.nzliveservice.websocket;

import com.alibaba.fastjson.JSONObject;
import com.example.nzliveservice.bean.NameRecord;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/websocket")
@Component
public class MyWebSocket {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount=0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet=new CopyOnWriteArraySet<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private String userid;

    private int OldHeartbeatDetection=11,NewHeartbeatDetection=12;

    Timer timer=new Timer();
    TimerTask timerTask=new TimerTask() {
        @Override
        public void run() {
//            System.out.println("run");
            if (OldHeartbeatDetection==NewHeartbeatDetection){
                webSocketSet.remove(this);
            }else {
                OldHeartbeatDetection=NewHeartbeatDetection;
            }
        }
    };

    /**
     * 连接建立成功调用的方法
     * @param session
     */
    @OnOpen
    public void OnOpen(Session session){
        this.session=session;
        webSocketSet.add(this);          //加入set中
        addOnlineCount();                 //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());

        timer.schedule(timerTask,0,7500);
//        try {
//            sendMessage("连接成功");
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("IO异常");
//        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);      //从set中删除
        subOnlineCount();                    //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
        timer.cancel();
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session){
        System.out.println("来自客户端的消息:"+ userid + message);
//        sendInfo(message);
        JSONObject jsonObject=JSONObject.parseObject(message);
        String type=jsonObject.getString("type");
        switch (type){
            case "userid":
                userid=jsonObject.getString("userid");
                System.out.println("新用户进入："+userid);
                break;
            case "checkTheBed":
                System.out.println("查寝");
                jsonObject=new JSONObject();
                jsonObject.put("type","checkTheBed");
//                sendInfo(jsonObject.toString());
                for (MyWebSocket socket:webSocketSet){
                    String userid=socket.userid;
                    if ("201604".equals(userid.substring(0,6))){
                        sendToOne(userid,jsonObject.toString());
                    }
                }
                break;
            case "returnCheckTheBed":
                String data=jsonObject.getString("data");
                jsonObject = new JSONObject();
                jsonObject.put("type","returnCheckTheBed");
                jsonObject.put("data",data);
                jsonObject.put("userid",this.userid);
                sendToOne("111111",jsonObject.toString());
                break;
            case "heartbeatDetection":
                int sData=jsonObject.getInteger("data");
                NewHeartbeatDetection=sData;
                break;
        }
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        timer.cancel();
        error.printStackTrace();
    }

    /**
     * 对当前用户发送消息
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 群发自定义消息
     * @param message
     */
    public static void sendInfo(String message) {
        for (MyWebSocket item : webSocketSet){
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                //发送失败结束本次发送对象
                continue;
            }
        }
    }

    /**
     * 对个人发消息
     * @param userid
     * @param message
     */
    public static void sendToOne(String userid,String message){
        for (MyWebSocket item : webSocketSet){
            try {
                if (userid.equals(item.userid)){
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        MyWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount(){
        MyWebSocket.onlineCount--;
    }
}
