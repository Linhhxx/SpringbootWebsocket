package com.example.compent;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author: lin_charles
 * @create: 2020-04-29 15:10:45
 * @program: SpringbootWebsocket
 * @description:    socketIO的启动类
 */

@Component
@Order(value = 1)
@Slf4j
public class ServerRunner implements CommandLineRunner {
    private final SocketIOServer socketIOServer;

    @Autowired
    public ServerRunner(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
    }


    @Override
    public void run(String... args) throws Exception {
        // spring服务启动后会紧接着启动run方法，即会吊起来socket服务。
        socketIOServer.start();
        log.info("socket.io启动");

    }
}
