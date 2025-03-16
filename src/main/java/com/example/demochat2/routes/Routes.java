package com.example.demochat2.routes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demochat2.controllers.WebhookController;
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
    public ResponseEntity<?> verifyWebhook(
            @RequestParam(value = "hub.mode", required = false) String mode,
            @RequestParam(value = "hub.verify_token", required = false) String token,
            @RequestParam(value = "hub.challenge", required = false) String challenge) {
        return webhookController.verifyWebhook(mode, token, challenge);
    }
}