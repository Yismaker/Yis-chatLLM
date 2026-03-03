package com.Yis.java.ai.langchain4j;

import com.Yis.java.ai.langchain4j.assistant.SeparateChatAssistant;
import com.Yis.java.ai.langchain4j.bean.ChatMessages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@SpringBootTest
public class MongoCrudTest {
    @Autowired
    private MongoTemplate mongoTemplate;
/**
 * 插入文档
 */
/* @Test
public void testInsert() {
mongoTemplate.insert(new ChatMessages(1L, "聊天记录"));
}*/
    /**
     * 插入文档
     */
    @Test
    public void testInsert2() {
        ChatMessages chatMessages = new ChatMessages();
        chatMessages.setContent("聊天记录列表");
        mongoTemplate.insert(chatMessages);
    }
    /**
     * 根据id查询文档
     */
    @Test
    public void testFindById() {
        ChatMessages chatMessages = mongoTemplate.findById("69a6352615f5d4708dd3a7c3",
                ChatMessages.class);
        System.out.println(chatMessages);
    }
    /**
     * 修改文档
     */
    @Test
    public void testUpdate() {
        Criteria criteria = Criteria.where("_id").is("69a6352615f5d4708dd3a7c3");
        Query query = new Query(criteria);
        Update update = new Update();
        update.set("content", "新的聊天记录列表");
//修改或新增
        mongoTemplate.upsert(query, update, ChatMessages.class);
    }
//    /**
//     * 新增或修改文档
//     */
//    @Test
//    public void testUpdate2() {
//        Criteria criteria = Criteria.where("_id").is("100");
//        Query query = new Query(criteria);
//        Update update = new Update();
//        update.set("content", "新的聊天记录列表");
////修改或新增
//        mongoTemplate.upsert(query, update, ChatMessages.class);
//    }
//    /**
//     * 删除文档
//     */
//    @Test
//    public void testDelete() {
//        Criteria criteria = Criteria.where("_id").is("100");
//        Query query = new Query(criteria);
//        mongoTemplate.remove(query, ChatMessages.class);
//    }

    @Autowired
    private SeparateChatAssistant separateChatAssistant;
    @Test
    public void testChatMemory5() {
        String answer1 = separateChatAssistant.chat(1,"我是Yis");
        System.out.println(answer1);
        String answer2 = separateChatAssistant.chat(1,"我是谁");
        System.out.println(answer2);
        String answer3 = separateChatAssistant.chat(2,"我是谁");
        System.out.println(answer3);
    }
    @Test
    public void testSystemMessage() {
        String answer = separateChatAssistant.chat2(1, "我是谁，我多大了", "翠花", 18);
        System.out.println(answer);
    }
}
