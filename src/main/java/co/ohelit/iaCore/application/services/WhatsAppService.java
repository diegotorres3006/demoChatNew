package co.ohelit.iaCore.application.services;

import co.ohelit.iaCore.domain.ports.in.MessageSenderIn;
import co.ohelit.iaCore.infrastructure.adapters.WhatsAppAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppService implements MessageSenderIn {

    private final WhatsAppAdapter whatsAppAdapter;

    @Autowired
    public WhatsAppService(WhatsAppAdapter whatsAppAdapter){
        this.whatsAppAdapter = whatsAppAdapter;
    }

    @Override
    public void sendMessage(String recipient, String text, String messageId) {
        this.whatsAppAdapter.sendMessage(recipient, text, messageId);
    }

}
