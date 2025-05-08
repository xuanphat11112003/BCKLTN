//package com.dxp.HeThongChuoiCungUng.ServiceImpl;//package com.dxp.HeThongChuoiCungUng.ServiceImpl;
//import com.dxp.HeThongChuoiCungUng.DTO.Request.ChatMessage;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class RedisService {
//    private final RedisTemplate<String, ChatMessage> redisTemplate;
//
//    public RedisService(RedisTemplate<String, ChatMessage> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//    public void saveMessage(String sessionId, ChatMessage message) {
//        redisTemplate.opsForList().rightPush("chat-messages:" + sessionId, message);
//    }
//
//    public List<ChatMessage> getMessages(String sessionId) {
//        return redisTemplate.opsForList().range("chat-messages:" + sessionId, 0, -1);
//    }
//}
