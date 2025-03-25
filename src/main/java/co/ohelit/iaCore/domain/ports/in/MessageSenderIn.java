package co.ohelit.iaCore.domain.ports.in;

import java.util.concurrent.CompletableFuture;

public interface MessageSenderIn {
    CompletableFuture<String> sendMessage(String recipient, String text, String messageId, boolean wait);
}
