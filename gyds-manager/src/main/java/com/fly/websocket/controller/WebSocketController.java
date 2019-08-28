package com.fly.websocket.controller;

import java.io.IOException;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import net.sf.json.JSONObject;

@Component
@ServerEndpoint("/pc/websocket/{sceneId}")
public class WebSocketController {
    
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    //private static CopyOnWriteArraySet<WebSocketController> webSocketSet = new CopyOnWriteArraySet<WebSocketController>();
    private static ConcurrentMap<String,WebSocketController>  webSocketMap = new ConcurrentHashMap<String,WebSocketController>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam("sceneId") String sceneId,Session session){
        this.session = session;
        webSocketMap.put(sceneId, this);
         //在线数加1
        System.out.println("唯一key为：" + sceneId);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("sceneId") String sceneId){
        webSocketMap.remove(sceneId);//从map中删除
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message,Session session) {
        System.out.println("来自客户端的消息:" + message);

        JSONObject jsonobject = JSONObject.fromObject(message);
        Hashtable params= (Hashtable)JSONObject.toBean(jsonobject,Hashtable.class);
        System.out.println("*****************************"+params.get("equipmentType"));
        //群发消息
        WebSocketController webSocketController = webSocketMap.get(params.get("equipmentType"));
        try {
            webSocketController.sendMessage((String)params.get("openId"));
        } catch (IOException e) {
            e.printStackTrace();
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
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }
    
}   