package com.example.compent;

import com.alibaba.fastjson.JSONObject;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * @author: lin_charles
 * @create: 2020-04-29 15:26:26
 * @program: SpringbootWebsocket
 * @description:
 */

@Component
@Slf4j
public class SocketIoServer {
    public static SocketIOServer socketIOServer;

    public static Map<UUID, SocketIOClient> clients = new ConcurrentHashMap<>();

    public static Map<Object,Map<String,Object>> onlineUsers = new HashMap<>();

    @Autowired
    public SocketIoServer(SocketIOServer server){
        this.socketIOServer = server;
    }

    @OnConnect      //用于监听客户端连接信息的
    public void onConnect(SocketIOClient client) {
        String sa = client.getRemoteAddress().toString();
        String clientIp = sa.substring(1, sa.indexOf(":"));// 获取设备ip
        log.info(clientIp + "-------------------------" + "a user connected");
    }

    /*@OnEvent用户后端监听前端的请求事件的。value值就是前端请求的唯一标识，前端携带这个请求的唯一标识进行请求后台，然后后台监听到这个请求，
    然后进行一系列操作。*/
    @OnEvent(value = "login")
    public void login(SocketIOClient client, AckRequest ackRequest, Map<String,Object> data){
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",data.get("projectId"));
        map.put("userId",data.get("userId"));
        onlineUsers.put(client.getSessionId(),map);
        clients.put(client.getSessionId(),client);
        log.info(data.get("userId")+"已经登录");
    }

    @OnDisconnect       //用户监听客户端断开信息的。
    public void onDisconnect(SocketIOClient client) {
        if(onlineUsers.containsKey(client.getSessionId())){
            log.info("失去连接**********************");
            onlineUsers.remove(client.getSessionId());
        }
    }

    @OnEvent("message")     //用于监听前端所发送的socket.send()请求。
    public void message(SocketIOClient client,Map<String,Object> data){
        for(Object key : onlineUsers.keySet()){
            if(data.get("projectId").equals(onlineUsers.get(key).get("projectId")) && data.get("toUserId").equals(onlineUsers.get(key).get("userId"))){
                log.info(onlineUsers.get(client.getSessionId()).get("userId")+"将消息发送给"+data.get("toUserId"));
                //向指定用户的前端推送消息，前端使用socket.on('message', function (data) {}接收
                clients.get(key).sendEvent("message",data.get("msg"));
            }
        }
    }





}
