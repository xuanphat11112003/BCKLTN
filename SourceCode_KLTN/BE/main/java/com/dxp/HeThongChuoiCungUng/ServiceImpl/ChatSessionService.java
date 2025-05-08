//package com.dxp.HeThongChuoiCungUng.ServiceImpl;
//
//import com.dxp.HeThongChuoiCungUng.DTO.Request.ChatMessage;
//
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//
//@Service
//public class ChatSessionService {
//    private final RedisTemplate<String, Object> redisTemplate;
//
//    public ChatSessionService(RedisTemplate<String, Object> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//    public void saveMessage(String chatId, ChatMessage message) {
//        redisTemplate.opsForList().rightPush("chat:" + chatId, message);
//    }
//
//    public List<Object> getMessages(String chatId) {
//        return redisTemplate.opsForList().range("chat:" + chatId, 0, -1);
//    }
//
//    public void clearChat(String chatId) {
//        redisTemplate.delete("chat:" + chatId);
//    }
//}
