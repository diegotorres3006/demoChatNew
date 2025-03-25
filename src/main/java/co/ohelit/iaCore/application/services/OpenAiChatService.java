package co.ohelit.iaCore.application.services;

import co.ohelit.iaCore.infrastructure.adapters.OpenAiAssistant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenAiChatService {

    private final OpenAiAssistant openAiAssistant;

    @Autowired
    public OpenAiChatService(OpenAiAssistant openAiAssistant){
        this.openAiAssistant = openAiAssistant;
    }

    public String chat(String userMessage) {
        return this.openAiAssistant.chat(userMessage);
    }
}
