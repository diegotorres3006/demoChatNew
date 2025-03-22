package co.ohelit.iaCore.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenService {

    private final WebClientService webClientService;
    private final Map<String, TokenCache> tokenCache = new ConcurrentHashMap<>();

    @Autowired
    public TokenService(WebClientService webClientService) {
        this.webClientService = webClientService;
    }

    public Mono<String> getToken(String clientId, String clientSecret, String fetchUrl) {
        String cacheKey = clientId + "_" + fetchUrl;

        TokenCache cachedToken = tokenCache.get(cacheKey);
        if (cachedToken != null && cachedToken.getExpiration().isAfter(Instant.now())) {
            return Mono.just(cachedToken.getToken());
        }

        String authHeader = "Basic " + java.util.Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());

        return webClientService.makeRequest(fetchUrl, HttpMethod.POST, "grant_type=client_credentials", null,
                        MediaType.APPLICATION_FORM_URLENCODED_VALUE, null, Map.of(HttpHeaders.AUTHORIZATION, authHeader))
                .flatMap(response -> {
                    if (!response.getStatusCode().is2xxSuccessful()) {
                        return Mono.error(new RuntimeException("Error obteniendo el token"));
                    }
                    return response.getBody() != null ? Mono.just(response.getBody()) : Mono.empty();
                })
                .map(body -> {
                    TokenResponse tokenResponse = new TokenResponse(body);
                    String token = tokenResponse.getAccessToken();
                    Instant expiration = Instant.now().plusSeconds(tokenResponse.getExpiresIn());
                    tokenCache.put(cacheKey, new TokenCache(token, expiration));
                    return token;
                });
    }
}