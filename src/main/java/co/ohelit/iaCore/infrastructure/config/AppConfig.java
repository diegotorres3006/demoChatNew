package co.ohelit.iaCore.infrastructure.config;
import co.ohelit.iaCore.application.services.RecipesService;
import co.ohelit.iaCore.application.services.YamlService;
import co.ohelit.iaCore.infrastructure.adapters.RecipesPortAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {
    @Value("${WEBHOOK_VERIFY_TOKEN}")
    private String webhookVerifyToken;

    @Value("${API_TOKEN}")
    private String apiToken;

    @Value("${BUSINESS_PHONE}")
    private String businessPhone;

    @Value("${API_VERSION}")
    private String apiVersion;

    @Value("${CHATGPT_API_KEY}")
    private String chatGptApiKey;

    @Value("${IS_CONTAINER}")
    private boolean isContainer;

    @Value("${QUYS_CLIENT_ID}")
    private String quysClientId;

    @Value("${QUYS_CLIENT_SECRET}")
    private String quysClientSecret;

    @Value("${QUYS_API_FETCH_URL}")
    private String quysApiFetchUrl;

    @Value("${server.port}")
    private int serverPort;

    // Getters
    public String getWebhookVerifyToken() { return webhookVerifyToken; }
    public String getApiToken() { return apiToken; }
    public String getBusinessPhone() { return businessPhone; }
    public String getApiVersion() { return apiVersion; }
    public String getChatGptApiKey() { return chatGptApiKey; }
    public boolean isContainer() { return isContainer; }
    public String getQuysClientId() { return quysClientId; }
    public String getQuysClientSecret() { return quysClientSecret; }
    public String getQuysApiFetchUrl() { return quysApiFetchUrl; }
    public int getServerPort() { return serverPort; }

    @Bean
    public RecipesService recipesService(RecipesPortAdapter recipesPortAdapter){
        return new RecipesService(recipesPortAdapter);
    }

    @Bean
    public YamlService yamlService(){
        return new YamlService();
    }

}
