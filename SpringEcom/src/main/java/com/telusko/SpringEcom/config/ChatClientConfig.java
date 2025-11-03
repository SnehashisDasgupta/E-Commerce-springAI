package com.telusko.SpringEcom.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class ChatClientConfig {

    @Value("${spring.ai.ollama.base-url}")
    private String ollamaBaseUrl;

    // Primary ChatClient using OpenRouter (for description generation)
    @Bean
    @Primary
    public ChatClient openRouterChatClient(@Qualifier("openAiChatModel") ChatModel openAiChatModel) {
        return ChatClient.builder(openAiChatModel).build();
    }

    // Secondary ChatClient using Ollama (for RAG chatbot)
    @Bean("ollamaChatClient")
    public ChatClient ollamaChatClient(@Qualifier("ollamaChatModel") ChatModel ollamaChatModel) {
        return ChatClient.builder(ollamaChatModel).build();
    }

    // Ollama Chat Model Bean
    @Bean("ollamaChatModel")
    public ChatModel ollamaChatModel() {
        OllamaApi ollamaApi = OllamaApi.builder()
                .baseUrl(ollamaBaseUrl)
                .build();

        return OllamaChatModel.builder()
                .ollamaApi(ollamaApi)
                .defaultOptions(OllamaOptions.builder()
                        .model("llama3.2:1b")
                        .temperature(0.7)
                        .build())
                .build();
    }

    // ✅ PRIMARY EMBEDDING MODEL - Ollama for Vector Store
    @Bean
    @Primary
    public EmbeddingModel primaryEmbeddingModel(@Qualifier("ollamaEmbeddingModel") EmbeddingModel ollamaEmbeddingModel) {
        return ollamaEmbeddingModel;
    }

    @Bean
    public VectorStore vectorStore(JdbcTemplate jdbcTemplate, EmbeddingModel embeddingModel) {
        return PgVectorStore.builder(jdbcTemplate, embeddingModel).build();
    }
}