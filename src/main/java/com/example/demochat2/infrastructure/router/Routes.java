package com.example.demochat2.infrastructure.router;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demochat2.infrastructure.controller.WebhookController;
import java.util.Map;

@RestController
@RequestMapping("/webhook")
public class Routes {

    private final WebhookController webhookController;

    public Routes(WebhookController webhookController) {
        this.webhookController = webhookController;
    }

    @PostMapping
    public ResponseEntity<?> handleIncoming(@RequestBody Map<String, Object> body) {
        return webhookController.handleIncoming(body);
    }

    @GetMapping
    public ResponseEntity<?> verifyWebhook(HttpServletRequest request) {
        return webhookController.verifyWebhook(request);
    }
}