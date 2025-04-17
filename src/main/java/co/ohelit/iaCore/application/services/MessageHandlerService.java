package co.ohelit.iaCore.application.services;

import co.ohelit.iaCore.domain.ports.in.MessageSenderIn;
import co.ohelit.iaCore.domain.ports.out.MessageSenderOut;
import co.ohelit.iaCore.domain.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Qualifier("messageSenderIn")
public class MessageHandlerService implements MessageSenderIn {

    private final MessageSenderOut messageSenderOut;
    private final MessageSenderService messageSenderService;


    @Autowired
    public MessageHandlerService(MessageSenderOut messageSenderOut,  MessageSenderService messageSenderService) {
        this.messageSenderOut = messageSenderOut;
        this.messageSenderService = messageSenderService;
    }

    private final Map<String, String> lastMessage = new ConcurrentHashMap<>();
    public void receiveMessage(Message message) {
        
        if (message != null && "text".equals(message.getType())) {
            String response = "Echo: " + message.getText().getBody();
            messageSenderOut.sendMessage(message.getFrom(), response, message.getId());
            messageSenderOut.markAsRead(message.getId());


        }
    }

}
