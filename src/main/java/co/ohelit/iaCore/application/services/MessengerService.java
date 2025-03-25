package co.ohelit.iaCore.application.services;

import co.ohelit.iaCore.domain.ports.in.MessageSenderIn;
import co.ohelit.iaCore.infrastructure.adapters.MessengerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class MessengerService implements MessageSenderIn {

    private final MessengerAdapter messengerAdapter;

    @Autowired
    public MessengerService(MessengerAdapter messengerAdapter){
        this.messengerAdapter = messengerAdapter;
    }

    @Override
    public CompletableFuture<String> sendMessage(String recipient, String text, String messageId, boolean wait) {
        messengerAdapter.sendMessage("asd", "aasd", "123");
        return null;
    }
}
