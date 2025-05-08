//package com.dxp.HeThongChuoiCungUng.Config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import io.socket.socketio.server.SocketIoServer;
//
//@Configuration
//public class SocketIoConfig {
//    @Bean
//    public SocketIoServer socketIoServer() {
//        SocketIoServer server = new SocketIoServer();
//
//        server.on("message", args -> {
//            // Phát lại tin nhắn cho tất cả client
//            server.getBroadcastOperations().emit("message", args[0]);
//        });
//
//        return server;
//    }
//}
