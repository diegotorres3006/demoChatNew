package co.ohelit.iaCore.domain.ports.out;

import java.util.concurrent.CompletableFuture;

public interface MessageSenderOut {
    void sendMessage(String recipient, String text, String messageId);
    void markAsRead(String messageId);
}
