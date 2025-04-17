package co.ohelit.iaCore.application.services;

import co.ohelit.iaCore.infrastructure.adapters.WhatsAppAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class MessageSenderService{

    private final WhatsAppAdapter whatsAppAdapter;


    @Autowired
    public MessageSenderService(WhatsAppAdapter whatsAppAdapter){
        this.whatsAppAdapter = whatsAppAdapter;
    }

    @Async
    public CompletableFuture<String> sendMessage(String recipient, String text, String messageId, boolean wait) {
        if(wait){
            CompletableFuture<String> futureResponse = new CompletableFuture<>();

            this.whatsAppAdapter.sendMessage(recipient, text, messageId);
            return futureResponse;
        } else {
            this.whatsAppAdapter.sendMessage(recipient, text, messageId);
            return null;
        }   
    }

}
