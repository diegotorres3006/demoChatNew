package com.example.demochat2.application.service;
import com.example.demochat2.infrastructure.adapter.WhatsAppService;
import com.example.demochat2.domain.model.Message;
import org.springframework.stereotype.Service;

@Service
public class MessageHandler {

    private final WhatsAppService whatsAppService;

    public MessageHandler(WhatsAppService whatsAppService) {
        this.whatsAppService = whatsAppService;
    }

    public void handleIncomingMessage(Message message) {
        if (message != null && "text".equals(message.getType())) {
            String response = "Echo: " + message.getText().getBody();
            // Suscribirse para ejecutar las llamadas asíncronas
            whatsAppService.sendMessage(message.getFrom(), response, message.getId())
                    .subscribe();

            whatsAppService.markAsRead(message.getId())
                    .subscribe();
        }
    }
}
