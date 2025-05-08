//package com.dxp.HeThongChuoiCungUng.Component;
//import com.dxp.HeThongChuoiCungUng.ServiceImpl.ChatSessionService;
//import org.springframework.context.ApplicationListener;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.messaging.SessionDisconnectEvent;
//
//@Component
//public class WebSocketEventListener implements ApplicationListener<SessionDisconnectEvent> {
//
//    private final ChatSessionService chatService;
//
//    public WebSocketEventListener(ChatSessionService chatService) {
//        this.chatService = chatService;
//    }
//
//    @Override
//    public void onApplicationEvent(SessionDisconnectEvent event) {
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//        String chatId = (String) headerAccessor.getSessionAttributes().get("chatId");
//
//        if (chatId != null) {
//            chatService.clearChat(chatId);
//            System.out.println("Cleared chat history for chatId: " + chatId);
//        }
//    }
//}
//
