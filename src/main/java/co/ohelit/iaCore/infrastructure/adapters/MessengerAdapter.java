package co.ohelit.iaCore.infrastructure.adapters;
import co.ohelit.iaCore.domain.ports.out.MessageSenderOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessengerAdapter implements MessageSenderOut {

    @Autowired
    public MessengerAdapter(){

    }

    @Override
    public void sendMessage(String recipient, String text, String messageId) {
        System.out.println("MEtodo de enviar mensaje en Messenger");
    }

    @Override
    public void markAsRead(String messageId) {

    }
}
