package com.example.config;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: lin_charles
 * @create: 2020-04-29 14:41:29
 * @program: SpringbootWebsocket
 * @description:  socketIo配置类，设置好端口，地址等
 */

@Configuration
public class NettySocketConfig {

    @Bean
    public SocketIOServer socketIOServer() throws Exception{
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname("localhost");
        config.setPort(9092);
        config.setMaxFramePayloadLength(1024 * 1024);
        config.setMaxHttpContentLength(1024 * 1024);
        config.setAllowCustomRequests(true);
        config.setBossThreads(1);
        config.setWorkerThreads(100);
        final SocketIOServer server = new SocketIOServer(config);
        return server;
    }

    /**
     * 注入OnConnect，OnDisconnect，OnEvent注解。 不写的话Spring无法扫描OnConnect，OnDisconnect等注解
     * @param socketServer
     * @return
     */
    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }

}
