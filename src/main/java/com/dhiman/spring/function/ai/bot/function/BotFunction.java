package com.dhiman.spring.function.ai.bot.function;

import com.dhiman.spring.function.ai.bot.ChatService;
import com.dhiman.spring.function.ai.bot.records.ChatInput;
import com.dhiman.spring.function.ai.bot.records.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;
import java.util.function.Function;

@Configuration
public class BotFunction {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ChatService chatService;
    public BotFunction(ChatService chatService) {
        this.chatService = chatService;
    }
    @Bean
    public Function<ChatInput,Message> chat() {
        return (chatInput)-> {
            logger.info("ChatInput received {}",chatInput);
            if(null == chatInput.messageText()){
                throw new RuntimeException("Empty chat");
            }
            return new Message(UUID.randomUUID().toString(),
                    false,
                    chatService.chatOnSpecificData(chatInput.messageText()),
                    System.currentTimeMillis());
        };
    }
}
