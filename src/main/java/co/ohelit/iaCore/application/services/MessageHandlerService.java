package co.ohelit.iaCore.application.services;

import co.ohelit.iaCore.domain.ports.in.MessageSenderIn;
import co.ohelit.iaCore.domain.ports.out.MessageSenderOut;
import co.ohelit.iaCore.domain.models.Message;
import co.ohelit.iaCore.infrastructure.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    private final RabbitTemplate rabbitTemplate;
    private boolean useRabbit = true;



    @Autowired
    public MessageHandlerService(MessageSenderOut messageSenderOut,  MessageSenderService messageSenderService, RabbitTemplate rabbitTemplate) {
        this.messageSenderOut = messageSenderOut;
        this.messageSenderService = messageSenderService;
        this.rabbitTemplate = rabbitTemplate;
    }

    private final Map<String, String> lastMessage = new ConcurrentHashMap<>();

    public void receiveMessage(Message message) {
        
        if (message != null && "text".equals(message.getType())) {
            String response = "Echo: " + message.getText().getBody();

            if (useRabbit) {
                //enviar mensaje a la cola
                rabbitTemplate.convertAndSend(
                        RabbitConfig.EXCHANGE_NAME,
                        "routing.key",
                        message.getText().getBody()
                );

                //messageSenderOut.sendMessage(message.getFrom(), response, message.getId());
                messageSenderOut.markAsRead(message.getId());
            }

        }
    }

}
