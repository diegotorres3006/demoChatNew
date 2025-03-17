package com.example.demochat2.infrastructure.adapters;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.example.demochat2.infrastructure.config.AppConfig;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.Map;

@Service
public class WhatsAppService {

    private final String apiVersion;
    private final String businessPhone;
    private final String apiToken;


    public WhatsAppService(AppConfig appConfig) {
        this.apiVersion = appConfig.getApiVersion();
        this.businessPhone = appConfig.getBusinessPhone();
        this.apiToken = appConfig.getApiToken();

    }

    private final WebClient webClient = WebClient.create("https://graph.facebook.com");

    public Mono<Void> sendMessage(String recipient, String text, String messageId) {
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

    public Mono<Void> markAsRead(String messageId) {
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


}
