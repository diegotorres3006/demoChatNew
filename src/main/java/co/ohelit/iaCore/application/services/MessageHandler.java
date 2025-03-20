package co.ohelit.iaCore.application.services;
import co.ohelit.iaCore.domain.ports.out.MessageSenderOut;
import co.ohelit.iaCore.domain.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageHandler {

    //private final WhatsAppService whatsAppService;
    private final MessageSenderOut messageSenderOut;

    @Autowired
    public MessageHandler( MessageSenderOut messageSenderOut) {
        this.messageSenderOut = messageSenderOut;
    }

    public void handleIncomingMessage(Message message) {
        if (message != null && "text".equals(message.getType())) {
            String response = "Echo: " + message.getText().getBody();
            messageSenderOut.sendMessage(message.getFrom(), response, message.getId());
            messageSenderOut.markAsRead(message.getId());
        }
    }
}
