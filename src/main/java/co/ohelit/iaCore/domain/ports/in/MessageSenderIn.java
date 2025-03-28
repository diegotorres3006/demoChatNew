package co.ohelit.iaCore.domain.ports.in;

import co.ohelit.iaCore.domain.models.Message;

import java.util.concurrent.CompletableFuture;

public interface MessageSenderIn {
    void receiveMessage(Message message);
}
