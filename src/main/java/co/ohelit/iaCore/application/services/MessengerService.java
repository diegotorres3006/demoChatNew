package co.ohelit.iaCore.application.services;

import co.ohelit.iaCore.domain.ports.in.MessageSenderIn;
import co.ohelit.iaCore.infrastructure.adapters.MessengerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessengerService implements MessageSenderIn {

    private final MessengerAdapter messengerAdapter;

    @Autowired
    public MessengerService(MessengerAdapter messengerAdapter){
        this.messengerAdapter = messengerAdapter;
    }

    @Override
    public void sendMessage(String recipient, String text, String messageId) {
        messengerAdapter.sendMessage("asd", "aasd", "123");
    }
}
