package com.Yis.java.ai.langchain4j.controller;

import com.Yis.java.ai.langchain4j.assistant.YisAgent;
import com.Yis.java.ai.langchain4j.bean.ChatForm;
import dev.langchain4j.agent.tool.P;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Tag(name = "YisController", description = "YisController")
@RestController
@RequestMapping("/yis")
public class YisController {
    @Autowired
    private YisAgent yisAgent;

    @Operation(summary = "对话")
//    @PostMapping("/chat")
//    public String chat(@RequestBody ChatForm chatForm){
//        return yisAgent.chat(chatForm.getMemoryId(), chatForm.getMessage());
//    }
    @PostMapping(value = "/chat", produces = "text/stream;charset=utf-8")
    public Flux<String> chat(@RequestBody ChatForm chatForm) {
        return yisAgent.chat(chatForm.getMemoryId(), chatForm.getMessage());
    }
}
