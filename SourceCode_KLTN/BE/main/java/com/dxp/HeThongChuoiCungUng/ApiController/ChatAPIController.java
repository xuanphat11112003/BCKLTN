//package com.dxp.HeThongChuoiCungUng.ApiController;
//import com.dxp.HeThongChuoiCungUng.DTO.Request.ChatMessage;
//import com.dxp.HeThongChuoiCungUng.ServiceImpl.ChatSessionService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class ChatAPIController {
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
//
//    @Autowired
//    private ChatSessionService chatSessionService;
//
//    @MessageMapping("/chat.sendNotification")
//    @SendTo("/topic/notifications")
//    @CrossOrigin
//    public void sendNotification(ChatMessage notification) {
//        System.out.println("Received notification: " + notification);
//        messagingTemplate.convertAndSend("/topic/notifications", notification);
//    }
//    @PostMapping("/api/chat/end-session/{chatId}")
//    @CrossOrigin
//    public ResponseEntity<Void> endChatSession(@PathVariable String chatId) {
//        chatSessionService.clearChat(chatId);
//        return ResponseEntity.ok().build();
//    }
//}
//
