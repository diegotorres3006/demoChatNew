package com.example.demochat2.domain.ports;

public interface MessageSenderPort {
    void sendMessage(String recipient, String text, String messageId);
    void markAsRead(String messageId);
}
