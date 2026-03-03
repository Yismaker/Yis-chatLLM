package com.Yis.java.ai.langchain4j.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(
        wiringMode = EXPLICIT,
        chatModel = "qwenChatModel",
        chatMemoryProvider = "Yis_chatMemoryProvider",
        tools = "appointmentTools",
        contentRetriever = "contentRetrieverYis"
)
public interface YisAgent {
    @SystemMessage(fromResource = "yis-prompt-template.txt")
    String chat(@MemoryId Long memoryId, @UserMessage String userMessage);
}
