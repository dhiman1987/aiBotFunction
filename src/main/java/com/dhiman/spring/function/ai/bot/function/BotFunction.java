package com.dhiman.spring.function.ai.bot.function;

import com.dhiman.spring.function.ai.bot.ChatService;
import com.dhiman.spring.function.ai.bot.IngestDataService;
import com.dhiman.spring.function.ai.bot.records.ChatInput;
import com.dhiman.spring.function.ai.bot.records.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.FileNotFoundException;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class BotFunction {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ChatService chatService;
    private final IngestDataService ingestDataService;

    public BotFunction(ChatService chatService, IngestDataService ingestDataService) {
        this.chatService = chatService;
        this.ingestDataService = ingestDataService;
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

    @Bean
    public Function<String,String> setupVectorStore(){
        return ingestDataService::setUpVectorStore;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        //config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
