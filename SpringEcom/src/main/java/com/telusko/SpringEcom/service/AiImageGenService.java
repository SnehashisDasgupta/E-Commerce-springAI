package com.telusko.SpringEcom.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AiImageGenService {

    @Value("${huggingface.api.token}")
    private String huggingFaceToken;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String API_URL = "https://api-inference.huggingface.co/models/stabilityai/stable-diffusion-xl-base-1.0";
    private static final int IMAGE_WIDTH = 1024;
    private static final int IMAGE_HEIGHT = 1024;

    public byte[] generateImage(String imagePrompt) {
        try {
            // 1️⃣ Build request body
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("inputs", imagePrompt);

            // 2️⃣ Prepare headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(huggingFaceToken);

            // 3️⃣ Wrap body + headers
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            // 4️⃣ Make POST call - Response is raw image bytes
            ResponseEntity<byte[]> response = restTemplate.exchange(
                    API_URL,
                    HttpMethod.POST,
                    request,
                    byte[].class
            );

            // 5️⃣ Return image bytes directly
            return response.getBody();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate image: " + e.getMessage(), e);
        }
    }

    public int getImageWidth() {
        return IMAGE_WIDTH;
    }

    public int getImageHeight() {
        return IMAGE_HEIGHT;
    }
}