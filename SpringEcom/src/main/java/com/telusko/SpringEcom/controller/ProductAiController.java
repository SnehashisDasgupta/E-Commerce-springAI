package com.telusko.SpringEcom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin
public class ProductAiController extends ProductController{

    @PostMapping("/product/generate-description")
    public ResponseEntity<String> generateDescription(@RequestParam String name, @RequestParam String category, @RequestParam String description){

        try{
            String aiDesc = productService.generateDescription(name, category, description);
            return new ResponseEntity<>(aiDesc, HttpStatus.OK);

        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("product/generate-image")
    public ResponseEntity<?> generateImage(@RequestParam String name, @RequestParam String category, @RequestParam String description){

        try {
            byte[] aiImage = productService.generateImage(name, category, description);
            return new ResponseEntity<>(aiImage, HttpStatus.OK);

        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
