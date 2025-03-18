package com.example.demochat2.infrastructure.controllers;
import com.example.demochat2.infrastructure.config.AppConfig;
import com.example.demochat2.domain.models.Message;
import com.example.demochat2.application.services.MessageHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class WebhookController {

    private final MessageHandler messageHandler;
    private final String webhookVerifyToken;

    public WebhookController(AppConfig appConfig, MessageHandler messageHandler) {
        this.webhookVerifyToken = appConfig.getWebhookVerifyToken();
        this.messageHandler = messageHandler;
    }

    @PostMapping
    public ResponseEntity<Void> handleIncoming(@RequestBody Map<String, Object> body) {
        try {
            List<?> entryList = (List<?>) body.get("entry");
            if (entryList != null && !entryList.isEmpty()) {
                Map<String, Object> entry = (Map<String, Object>) entryList.get(0);
                List<?> changesList = (List<?>) entry.get("changes");
                if (changesList != null && !changesList.isEmpty()) {
                    Map<String, Object> changes = (Map<String, Object>) changesList.get(0);
                    Map<String, Object> value = (Map<String, Object>) changes.get("value");
                    if (value != null) {
                        List<?> messagesList = (List<?>) value.get("messages");
                        if (messagesList != null && !messagesList.isEmpty()) {
                            Map<String, Object> messageMap = (Map<String, Object>) messagesList.get(0);

                            // Convertir el mapa en un objeto Message
                            ObjectMapper objectMapper = new ObjectMapper();
                            Message message = objectMapper.convertValue(messageMap, Message.class);

                            // Procesar el mensaje // Delegar la lógica al caso de uso
                            messageHandler.handleIncomingMessage(message);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<String> verifyWebhook(HttpServletRequest request) {
        String mode = request.getParameter("hub.mode");
        String token = request.getParameter("hub.verify_token");
        String challenge = request.getParameter("hub.challenge");

        if ("subscribe".equals(mode) && webhookVerifyToken.equals(token)) {
            System.out.println("Webhook verified successfully!");
            return ResponseEntity.ok(challenge);
        } else {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }
}