package co.ohelit.iaCore.infrastructure.adapters;
import co.ohelit.iaCore.domain.ports.in.MessageSenderIn;
import co.ohelit.iaCore.infrastructure.config.AppConfig;
import co.ohelit.iaCore.domain.models.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class MessageIncomingAdapter {

    private final String webhookVerifyToken;
    private final MessageSenderIn messageSenderIn;

    public MessageIncomingAdapter(AppConfig appConfig,
                                  @Qualifier("messageSenderIn") MessageSenderIn messageSenderIn) {
        this.webhookVerifyToken = appConfig.getWebhookVerifyToken();
        this.messageSenderIn = messageSenderIn;
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
                            //messageHandler.handleIncomingMessage(message);
                            System.out.println("holaaa");
                            this.messageSenderIn.receiveMessage(message);
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