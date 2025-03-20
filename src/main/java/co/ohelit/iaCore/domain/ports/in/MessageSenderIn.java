package co.ohelit.iaCore.domain.ports.in;

public interface MessageSenderIn {
    void sendMessage(String recipient, String text, String messageId);
}
