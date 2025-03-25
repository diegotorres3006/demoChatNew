package co.ohelit.iaCore.application.services;

import co.ohelit.iaCore.domain.ports.in.MessageSenderIn;
import co.ohelit.iaCore.infrastructure.adapters.WhatsAppAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WhatsAppService implements MessageSenderIn {

    private final WhatsAppAdapter whatsAppAdapter;
    @Autowired
    private PromesasService promesasService;

    @Autowired
    public WhatsAppService(WhatsAppAdapter whatsAppAdapter){
        this.whatsAppAdapter = whatsAppAdapter;
    }

    @Async
    @Override
    public CompletableFuture<String> sendMessage(String recipient, String text, String messageId, boolean wait) {
        if(wait){
            CompletableFuture<String> futureResponse = new CompletableFuture<>();
            this.promesasService.agregarRespuestaPendiente(recipient, futureResponse);
            this.whatsAppAdapter.sendMessage(recipient, text, messageId);
            return futureResponse;
        } else {
            this.whatsAppAdapter.sendMessage(recipient, text, messageId);
            return null;
        }
    }

}
