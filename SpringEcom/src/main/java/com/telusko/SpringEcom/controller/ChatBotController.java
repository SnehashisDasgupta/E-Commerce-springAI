package com.telusko.SpringEcom.controller;

import com.telusko.SpringEcom.service.ChatBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/chat")
public class ChatBotController {

    @Autowired
    private ChatBotService chatBotService;

    @GetMapping("/ask")
    public ResponseEntity<String> askBot(@RequestParam String message){

        String response = chatBotService.getBotResponse(message);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
