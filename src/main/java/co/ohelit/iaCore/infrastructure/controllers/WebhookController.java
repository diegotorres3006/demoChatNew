package co.ohelit.iaCore.infrastructure.controllers;
import co.ohelit.iaCore.infrastructure.config.AppConfig;
import co.ohelit.iaCore.domain.models.Message;
import co.ohelit.iaCore.application.services.MessageHandler;
import co.ohelit.iaCore.utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static co.ohelit.iaCore.utils.MapUtils.getFirstFromList;
import static co.ohelit.iaCore.utils.MapUtils.getNestedValue;

@RestController
public class WebhookController {

    private final MessageHandler messageHandler;
    private final String webhookVerifyToken;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public WebhookController(AppConfig appConfig, MessageHandler messageHandler) {
        this.webhookVerifyToken = appConfig.getWebhookVerifyToken();
        this.messageHandler = messageHandler;
    }

    public ResponseEntity<Void> handleIncoming(@RequestBody Map<String, Object> body) {
        try {
            Optional<Map<String, Object>> entryOpt = getFirstFromList(body, "entry");
            Optional<Map<String, Object>> changesOpt = entryOpt.flatMap(entry -> getFirstFromList(entry, "changes"));
            Optional<Map<String, Object>> valueOpt = changesOpt.flatMap(changes -> getNestedValue(changes, "value"));
            Optional<Map<String, Object>> messageOpt = valueOpt.flatMap(value -> getFirstFromList(value, "messages"));

            messageOpt.ifPresent(messageMap -> {
                ObjectMapper objectMapper = new ObjectMapper();
                Message message = objectMapper.convertValue(messageMap, Message.class);
                messageHandler.handleIncomingMessage(message);
            });
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