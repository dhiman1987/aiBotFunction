package com.dhiman.spring.function.ai.bot.records;

public record Message( String  id,
                       boolean  isUserInput,
                       String messageText,
                       long messageSentOn) {
}
