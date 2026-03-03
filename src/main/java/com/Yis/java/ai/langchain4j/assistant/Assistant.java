package com.Yis.java.ai.langchain4j.assistant;

import dev.langchain4j.service.spring.AiService;

@AiService
public interface Assistant {
    String chat(String userMessage);
}
