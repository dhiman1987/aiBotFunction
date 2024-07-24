package com.dhiman.spring.function.ai.bot;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatService {
    private final ChatClient chatClient;
    private final VectorStore vectors;

    @Value("classpath:prompt.st")
    private Resource promptResource;

    public ChatService(ChatClient chatClient, VectorStore vectors) {
        this.chatClient = chatClient;
        this.vectors = vectors;
    }
    public Map<String, String> chat(String message) {
        return Map.of("generation", chatClient.call(message));
    }
    public String chatOnSpecificData(String question) {

        List<Document> documents = this.vectors.similaritySearch(question);
        String inlined = documents.stream()
                .map(Document::getContent)
                .collect(Collectors.joining(System.lineSeparator()));

        Map<String,Object> params =  new HashMap<>(2);
        params.put("question",question);
        params.put("documents", inlined);

        Message userMessage = new UserMessage(question);
        PromptTemplate systemPromptTemplate = new SystemPromptTemplate(promptResource);
        Message systemMessage = systemPromptTemplate.createMessage(params);
        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));

        return chatClient.call(prompt).getResult().getOutput().getContent();
    }
}
