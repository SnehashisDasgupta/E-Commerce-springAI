package com.telusko.SpringEcom.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatBotService {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private VectorStore vectorStore;

    @Autowired
    @Qualifier("ollamaChatClient")
    private ChatClient chatClient;

    public String getBotResponse(String userQuery) {
        try {
            // Fetch context from vector store
            String context = fetchSemanticContext(userQuery);

            // ✅ DEBUG: Print context
            System.out.println("=== DEBUG: Context Retrieved ===");
            System.out.println("Query: " + userQuery);
            System.out.println("Context length: " + context.length());
            System.out.println("Context: " + context);
            System.out.println("================================");

            // If no relevant context found, return helpful message
            if (context.trim().isEmpty()) {
                return "I couldn't find any products matching your query. Please try asking about our available products or browse our catalog.";
            }

            // Read prompt template
            String promptStringTemplate = Files.readString(
                    resourceLoader.getResource("classpath:prompts/chatbot-rag-prompt.st")
                            .getFile()
                            .toPath()
            );

            // Prepare variables
            Map<String, Object> variables = new HashMap<>();
            variables.put("userQuery", userQuery);
            variables.put("context", context);

            // Create prompt
            PromptTemplate promptTemplate = PromptTemplate.builder()
                    .template(promptStringTemplate)
                    .variables(variables)
                    .build();

            // ✅ DEBUG: Print final prompt
            String finalPrompt = promptTemplate.create().getContents();
            System.out.println("=== DEBUG: Final Prompt ===");
            System.out.println(finalPrompt);
            System.out.println("===========================");

            // // ✅ DEBUG: Print Response
            String response = chatClient.prompt(promptTemplate.create()).call().content();
            System.out.println("=== DEBUG: AI Response ===");
            System.out.println(response);


            // Get response from Ollama
            return chatClient.prompt(promptTemplate.create()).call().content();

        } catch (IOException e) {
            throw new RuntimeException("Error reading prompt template: " + e.getMessage(), e);
        }
    }

    private String fetchSemanticContext(String userQuery) {
        List<Document> documents = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(userQuery)
                        .topK(5)
                        .similarityThreshold(0.3) // ✅ Lowered even more to 0.3
                        .build()
        );

        // ✅ DEBUG: Print search results
        System.out.println("=== DEBUG: Vector Search Results ===");
        System.out.println("Query: " + userQuery);
        System.out.println("Documents found: " + documents.size());
        System.out.println("====================================");

        if (documents.isEmpty()) {
            return "";
        }

        StringBuilder context = new StringBuilder();
        context.append("=== AVAILABLE PRODUCTS ===\n\n");

        for (int i = 0; i < documents.size(); i++) {
            Document doc = documents.get(i);
            context.append("Product ").append(i + 1).append(":\n");
            context.append(doc.getFormattedContent()).append("\n\n");
        }

        return context.toString();
    }
}
