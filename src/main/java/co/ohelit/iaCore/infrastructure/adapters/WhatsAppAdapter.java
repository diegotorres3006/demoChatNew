
package co.ohelit.iaCore.infrastructure.adapters;

import co.ohelit.iaCore.infrastructure.config.AppConfig;
import co.ohelit.iaCore.domain.ports.MessageSenderPort;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import co.ohelit.iaCore.Utils;

import java.util.Map;

@Component
public class WhatsAppAdapter implements MessageSenderPort {

    private final String apiVersion;
    private final String businessPhone;
    private final String apiToken;
    private final WebClient webClient;
    private final Utils utils;


    public WhatsAppAdapter(AppConfig appConfig, Utils utils) {
        this.apiVersion = appConfig.getApiVersion();
        this.businessPhone = appConfig.getBusinessPhone();
        this.apiToken = appConfig.getApiToken();
        this.webClient = WebClient.create("https://graph.facebook.com");
        this.utils = utils;
    }

    @Override
    public void sendMessage(String recipient, String text, String messageId) {
        String url = String.format("https://graph.facebook.com/%s/%s/messages", apiVersion, businessPhone);

        Map<String, Object> body = Map.of(
                "messaging_product", "whatsapp",
                "to", recipient,
                "text", Map.of("body", text),
                "context", Map.of("message_id", messageId)
        );

        utils.makeRequest(
                url,
                HttpMethod.POST,
                Utils.toJson(body),
                apiToken,
                "application/json",
                null,
                null
        ).subscribe(response -> {
            System.out.println("Respuesta de WhatsApp: " + response.getBody());
        });
    }
    @Override
    public void markAsRead(String messageId) {
        String url = String.format("https://graph.facebook.com/%s/%s/messages", apiVersion, businessPhone);

        Map<String, Object> body = Map.of(
                "messaging_product", "whatsapp",
                "status", "read",
                "message_id", messageId
        );

        utils.makeRequest(
                url,
                HttpMethod.POST,
                Utils.toJson(body),
                apiToken,
                "application/json",
                null,
                null
        ).subscribe(response -> {
            System.out.println("Mensaje marcado como leído: " + response.getBody());
        });
    }
}
