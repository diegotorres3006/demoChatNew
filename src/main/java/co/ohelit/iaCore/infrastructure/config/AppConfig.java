package co.ohelit.iaCore.infrastructure.config;
import org.springframework.beans.factory.annotation.Value;
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


    @Value("${server.port}")
    private int serverPort;

    // Getters
    public String getWebhookVerifyToken() { return webhookVerifyToken; }
    public String getApiToken() { return apiToken; }
    public String getBusinessPhone() { return businessPhone; }
    public String getApiVersion() { return apiVersion; }
    public int getServerPort() { return serverPort; }

}
