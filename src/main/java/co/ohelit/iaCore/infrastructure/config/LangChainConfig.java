package co.ohelit.iaCore.infrastructure.config;


import dev.langchain4j.http.client.jdk.JdkHttpClient;
import dev.langchain4j.http.client.jdk.JdkHttpClientBuilder;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class LangChainConfig {
private final String chatGptApiKey;

public LangChainConfig(AppConfig appConfig) {
    this.chatGptApiKey = appConfig.getChatGptApiKey();
}


    @Bean(name = "openAiChatModel")
    public ChatLanguageModel openAiChatModel() {
        JdkHttpClientBuilder jdkHttpClientBuilder = JdkHttpClient.builder();
        return OpenAiChatModel.builder()
                .httpClientBuilder(jdkHttpClientBuilder)
                .apiKey(chatGptApiKey)
                .modelName("gpt-4o-mini")
                .build();
    }
    @Bean(name = "ollamaChatModel")
    public ChatLanguageModel ollamaChatModel() {
        JdkHttpClientBuilder jdkHttpClientBuilder = JdkHttpClient.builder();
        return OllamaChatModel.builder()
                .httpClientBuilder(jdkHttpClientBuilder)
                .baseUrl("http://localhost:11434")
                .modelName("llama3.2:latest")
                .build();
    }
}
