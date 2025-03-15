package com.example.demochat2;

import com.example.demochat2.config.AppConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private final AppConfig appConfig;
    private final String apiVersion;
    private final String businessPhone;
    private final String apiToken;
    private final String webhookVerifyToken;

    public WebhookController(AppConfig appConfig) {
        this.appConfig = appConfig;
        this.apiVersion = appConfig.getApiVersion();
        this.businessPhone = appConfig.getBusinessPhone();
        this.apiToken = appConfig.getApiToken();
        this.webhookVerifyToken = appConfig.getWebhookVerifyToken();
    }


    private final WebClient webClient = WebClient.create("https://graph.facebook.com");

    @PostMapping
    public Mono<Void> handleWebhook(@RequestBody Map<String, Object> body) {
        System.out.println("Incoming webhook message: " + body);

        Map<String, Object> entry = (Map<String, Object>) ((java.util.List<?>) body.get("entry")).get(0);
        Map<String, Object> changes = (Map<String, Object>) ((java.util.List<?>) entry.get("changes")).get(0);
        Map<String, Object> value = (Map<String, Object>) changes.get("value");

        if (value.containsKey("messages")) {
            Map<String, Object> message = (Map<String, Object>) ((java.util.List<?>) value.get("messages")).get(0);

            if ("text".equals(message.get("type"))) {
                String sender = (String) message.get("from");
                String text = (String) ((Map<String, Object>) message.get("text")).get("body");
                String messageId = (String) message.get("id");

                return this.sendMessage(sender, text, messageId)
                        .then(this.markMessageAsRead(messageId));
            }
        }
        return Mono.empty();
    }

    @GetMapping
    public ResponseEntity<String> verifyWebhook(
            @RequestParam(value = "hub.mode", required = false) String mode,
            @RequestParam(value = "hub.verify_token", required = false) String token,
            @RequestParam(value = "hub.challenge", required = false) String challenge) {

        if ("subscribe".equals(mode) && webhookVerifyToken.equals(token)) {
            System.out.println("Webhook verified successfully!");
            return ResponseEntity.ok(challenge);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("403 Forbidden - Invalid verification request");
    }

    private Mono<Void> sendMessage(String recipient, String text, String messageId) {
        return webClient.post()
                .uri(String.format("/%s/%s/messages", apiVersion, businessPhone))
                .headers(headers -> headers.setBearerAuth(apiToken))
                .bodyValue(Map.of(
                        "messaging_product", "whatsapp",
                        "to", recipient,
                        "text", Map.of("body", "Tu mensaje en Java -> " + text),
                        "context", Map.of("message_id", messageId)
                ))
                .retrieve()
                .bodyToMono(Void.class);
    }

    private Mono<Void> markMessageAsRead(String messageId) {
        return webClient.post()
                .uri(String.format("/%s/%s/messages", apiVersion, businessPhone))
                .headers(headers -> headers.setBearerAuth(apiToken))
                .bodyValue(Map.of(
                        "messaging_product", "whatsapp",
                        "status", "read",
                        "message_id", messageId
                ))
                .retrieve()
                .bodyToMono(Void.class);
    }

    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("<pre>Nothing to see here.\nCheckout README.md to start.</pre>");
    }

}