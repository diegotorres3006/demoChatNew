package co.ohelit.iaCore.infrastructure.controllers;

import co.ohelit.iaCore.infrastructure.adapters.OllamaAssistant;
import co.ohelit.iaCore.infrastructure.adapters.OpenAiAssistant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final OpenAiAssistant openAiAssistant;
    private final OllamaAssistant ollamaAssistant;

    public ChatController(OpenAiAssistant openAiAssistant, OllamaAssistant ollamaAssistant) {
        this.openAiAssistant = openAiAssistant;
        this.ollamaAssistant = ollamaAssistant;
    }

    @GetMapping("/chat")
    public String model(
            @RequestParam(value = "message", defaultValue = "Hola, ¿quién eres?, di tu nombre y creador") String message,
            @RequestParam(value = "model", defaultValue = "openai") String model) {

        if ("ollama".equalsIgnoreCase(model)) {
            return ollamaAssistant.chat(message);
        }
        return openAiAssistant.chat(message);
    }
}