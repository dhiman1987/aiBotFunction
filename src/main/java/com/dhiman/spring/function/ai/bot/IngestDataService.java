package com.dhiman.spring.function.ai.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;

@Service
public class IngestDataService{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final VectorStore vectorStore;

    public IngestDataService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }
    public String setUpVectorStore(String fileName){
            TextReader reader = new TextReader(fileName);
            TokenTextSplitter splitter = new TokenTextSplitter();
            List<Document> documents = splitter.apply(reader.get());
            logger.debug("updating up vector store.");
            vectorStore.add(documents);
            logger.debug("setting up vector store completed.");
            return "Vector store updated successfully";
    }
}
