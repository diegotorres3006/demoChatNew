package co.ohelit.iaCore.application.services;
import co.ohelit.iaCore.domain.ports.MessageSenderPort;
import co.ohelit.iaCore.domain.models.Message;
import org.springframework.stereotype.Service;

@Service
public class MessageHandler {

    //private final WhatsAppService whatsAppService;
    private final MessageSenderPort messageSender;

    public MessageHandler( MessageSenderPort messageSender) {
        this.messageSender = messageSender;
    }

    public void handleIncomingMessage(Message message) {
        if (message != null && "text".equals(message.getType())) {
            String response = "Echo: " + message.getText().getBody();
            messageSender.sendMessage(message.getFrom(), response, message.getId());
            messageSender.markAsRead(message.getId());
        }
    }
}
