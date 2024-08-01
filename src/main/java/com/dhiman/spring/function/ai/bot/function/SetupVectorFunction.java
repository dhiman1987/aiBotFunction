package com.dhiman.spring.function.ai.bot.function;

import com.dhiman.spring.function.ai.bot.IngestDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class SetupVectorFunction {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final IngestDataService ingestDataService;
    public SetupVectorFunction(IngestDataService ingestDataService) {
        this.ingestDataService = ingestDataService;
    }
    @Bean
    public Function<String,String> setupVectorStore(){
        return ingestDataService::setUpVectorStore;
    }
}
