package co.ohelit.iaCore.utils;
import co.ohelit.iaCore.infrastructure.config.AppConfig;
import co.ohelit.iaCore.utils.TokenService;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
@Component
public class QuysUtils {

    //Para obtener token
    private final String quysClientID;
    private final String quysClientSecret;
    private final String quysApiFetchUrl;
    private final TokenService tokenService;

    public QuysUtils(TokenService tokenService, AppConfig appConfig) {
        this.quysClientID = appConfig.getQuysClientId();
        this.quysClientSecret = appConfig.getQuysClientSecret();
        this.quysApiFetchUrl = appConfig.getQuysApiFetchUrl();
        this.tokenService = tokenService;
    }

    public String getQuysToken() {
        return tokenService.getToken(quysClientID, quysClientSecret, quysApiFetchUrl).block();
    }
}
