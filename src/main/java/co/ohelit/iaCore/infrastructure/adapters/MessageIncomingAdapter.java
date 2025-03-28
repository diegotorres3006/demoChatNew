package co.ohelit.iaCore.infrastructure.adapters;
import co.ohelit.iaCore.domain.ports.in.MessageSenderIn;
import co.ohelit.iaCore.infrastructure.config.AppConfig;
import co.ohelit.iaCore.domain.models.Message;
import co.ohelit.iaCore.application.services.MessageHandlerService;
import co.ohelit.iaCore.utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static co.ohelit.iaCore.utils.MapUtils.getFirstFromList;
import static co.ohelit.iaCore.utils.MapUtils.getNestedValue;

@RestController
public class MessageIncomingAdapter {

    private final String webhookVerifyToken;
    private final MessageSenderIn messageSenderIn;

    public MessageIncomingAdapter(AppConfig appConfig,
                                  @Qualifier("messageSenderIn") MessageSenderIn messageSenderIn) {
        this.webhookVerifyToken = appConfig.getWebhookVerifyToken();
        this.messageSenderIn = messageSenderIn;
    }

    public ResponseEntity<Void> handleIncoming(@RequestBody Map<String, Object> body) {
        try {
            Optional<Map<String, Object>> entryOpt = getFirstFromList(body, "entry");
            Optional<Map<String, Object>> changesOpt = entryOpt.flatMap(entry -> getFirstFromList(entry, "changes"));
            Optional<Map<String, Object>> valueOpt = changesOpt.flatMap(changes -> getNestedValue(changes, "value"));
            Optional<Map<String, Object>> messageOpt = valueOpt.flatMap(value -> getFirstFromList(value, "messages"));
                 
            messageOpt.ifPresent(messageMap -> {
            // Convertir el mapa en un objeto Message
            ObjectMapper objectMapper = new ObjectMapper();
            Message message = objectMapper.convertValue(messageMap, Message.class);
            System.out.println("holaaa");
            this.messageSenderIn.receiveMessage(message);
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