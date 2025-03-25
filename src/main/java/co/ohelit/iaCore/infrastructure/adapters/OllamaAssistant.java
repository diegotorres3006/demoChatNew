package co.ohelit.iaCore.infrastructure.adapters;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(wiringMode = EXPLICIT, chatModel = "ollamaChatModel")
public interface OllamaAssistant {

    @SystemMessage("You are a polite assistant")
    String chat(String userMessage);
}