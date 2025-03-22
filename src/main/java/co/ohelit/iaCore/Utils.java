//package co.ohelit.iaCore;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.io.IOException;
//import java.time.Instant;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//@Component
//public class Utils {
//
//
//    private final WebClient webClient;
//    private final Map<String, TokenCache> tokenCache = new ConcurrentHashMap<>();
//    private static final ObjectMapper mapper = new ObjectMapper();
//
//    @Autowired
//    public Utils(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder.build();
//    }
//
//    public static String toJson(Object object) {
//        try {
//            return mapper.writeValueAsString(object);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Error al convertir el objeto a JSON", e);
//        }
//    }
//
//    public Mono<ResponseEntity<String>> makeRequest(
//            String url,
//            HttpMethod method,
//            String body,
//            String token,
//            String contentType,
//            Map<String, String> params,
//            Map<String, String> extraHeaders) {
//
//        WebClient.RequestBodySpec requestSpec = webClient.method(method)
//                .uri(url)
//                .headers(headers -> {
//                    if (token != null && !token.isEmpty()) {
//                        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
//                    }
//                    if (contentType != null && !contentType.isEmpty()) {
//                        headers.setContentType(MediaType.valueOf(contentType));
//                    }
//                    if (params != null) {
//                        params.forEach(headers::set);
//                    }
//                    if (extraHeaders != null) {
//                        extraHeaders.forEach(headers::set);
//                    }
//                });
//
//        return requestSpec
//                .bodyValue(body != null ? body : "")
//                .retrieve()
//                .toEntity(String.class)
//                .onErrorResume(e -> {
//                    System.err.println("Error en la petición: " + e.getMessage());
//                    return Mono.just(ResponseEntity.internalServerError().body("Error en la petición"));
//                });
//    }
//
//
//    public Mono<String> getToken(String clientId, String clientSecret, String fetchUrl) {
//        String cacheKey = clientId + "_" + fetchUrl;
//
//        TokenCache cachedToken = tokenCache.get(cacheKey);
//        if (cachedToken != null && cachedToken.getExpiration().isAfter(Instant.now())) {
//            return Mono.just(cachedToken.getToken());
//        }
//
//        String authHeader = "Basic " + java.util.Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
//
//        return makeRequest(fetchUrl, HttpMethod.POST, "grant_type=client_credentials", null,
//                MediaType.APPLICATION_FORM_URLENCODED_VALUE, null, Map.of(HttpHeaders.AUTHORIZATION, authHeader))
//                .flatMap(response -> {
//                    if (!response.getStatusCode().is2xxSuccessful()) {
//                        return Mono.error(new RuntimeException("Error obteniendo el token"));
//                    }
//                    return response.getBody() != null ? Mono.just(response.getBody()) : Mono.empty();
//                })
//                .map(body -> {
//                    TokenResponse tokenResponse = new TokenResponse(body);
//                    String token = tokenResponse.getAccessToken();
//                    Instant expiration = Instant.now().plusSeconds(tokenResponse.getExpiresIn());
//                    tokenCache.put(cacheKey, new TokenCache(token, expiration));
//                    return token;
//                });
//    }
//    //Estática para instanciar directamente
//    private static class TokenCache {
//        private final String token;
//        private final Instant expiration;
//
//        public TokenCache(String token, Instant expiration) {
//            this.token = token;
//            this.expiration = expiration;
//        }
//
//        public String getToken() {
//            return token;
//        }
//
//        public Instant getExpiration() {
//            return expiration;
//        }
//
//    }
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public static class TokenResponse {
//        @JsonProperty("access_token")
//        private String accessToken;
//
//        @JsonProperty("expires_in")
//        private long expiresIn;
//
//
//        // Constructor sin argumentos (requerido por Jackson)
//        public TokenResponse() {}
//
//        // Constructor para parsear JSON
//        public TokenResponse(String json) {
//            try {
//                ObjectMapper mapper = new ObjectMapper();
//                TokenResponse parsed = mapper.readValue(json, TokenResponse.class);
//                this.accessToken = parsed.accessToken;
//                this.expiresIn = parsed.expiresIn;
//            } catch (IOException e) {
//                throw new RuntimeException("Error parsing token response", e);
//            }
//        }
//
//        public String getAccessToken() {
//            return accessToken;
//        }
//
//        public long getExpiresIn() {
//            return expiresIn;
//        }
//
//
//    }
//
//
//
//}
//
//


