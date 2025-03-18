
package com.example.demochat2.infrastructure.adapters;

import com.example.demochat2.infrastructure.config.AppConfig;
import com.example.demochat2.domain.ports.MessageSenderPort;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class WhatsAppAdapter implements MessageSenderPort {

    private final String apiVersion;
    private final String businessPhone;
    private final String apiToken;
    private final WebClient webClient;

    public WhatsAppAdapter(AppConfig appConfig) {
        this.apiVersion = appConfig.getApiVersion();
        this.businessPhone = appConfig.getBusinessPhone();
        this.apiToken = appConfig.getApiToken();
        this.webClient = WebClient.create("https://graph.facebook.com");
    }

    @Override
    public void sendMessage(String recipient, String text, String messageId) {
        webClient.post()
                .uri(String.format("/%s/%s/messages", apiVersion, businessPhone))
                .headers(headers -> headers.setBearerAuth(apiToken))
                .bodyValue(Map.of(
                        "messaging_product", "whatsapp",
                        "to", recipient,
                        "text", Map.of("body", "Tu mensaje en Java -> " + text),
                        "context", Map.of("message_id", messageId)
                ))
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
    }

    @Override
    public void markAsRead(String messageId) {
        webClient.post()
                .uri(String.format("/%s/%s/messages", apiVersion, businessPhone))
                .headers(headers -> headers.setBearerAuth(apiToken))
                .bodyValue(Map.of(
                        "messaging_product", "whatsapp",
                        "status", "read",
                        "message_id", messageId
                ))
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
    }
}
