package com.harsh.sondhi.vector.db.demo.service;

import com.harsh.sondhi.vector.db.demo.record.Answer;
import com.harsh.sondhi.vector.db.demo.record.Question;
import com.harsh.sondhi.vector.db.demo.store.SimpleRAGVectorStore;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LLMService {
    private final ChatClient aiClient;
    private final SimpleRAGVectorStore simpleRAGVectorStore;

    @Value("classpath:/rag-prompt-template.st")
    private Resource ragPromptTemplate;

    public LLMService(ChatClient aiClient, SimpleRAGVectorStore simpleRAGVectorStore) {
        this.aiClient = aiClient;
        this.simpleRAGVectorStore = simpleRAGVectorStore;
    }

    public Answer ask( Question question){
        VectorStore vectorStore = simpleRAGVectorStore.getSimpleVectorStore();

        List<Document> similarDocuments = vectorStore.similaritySearch(SearchRequest.query(question.question()).withTopK(2));
        List<String> contentList = similarDocuments.stream().map(Document::getContent).toList();
        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate);
        Map<String, Object> promptParameters = new HashMap<>();
        promptParameters.put("input", question.question());
        promptParameters.put("documents", String.join("\n", contentList));
        Prompt prompt = promptTemplate.create(promptParameters);

        ChatResponse response = aiClient.call(prompt);

        return new Answer(response.getResult().getOutput().getContent());
    }
}
