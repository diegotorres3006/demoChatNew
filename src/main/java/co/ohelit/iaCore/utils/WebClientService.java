package co.ohelit.iaCore.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class WebClientService {

    private final WebClient webClient;

    @Autowired
    public WebClientService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Mono<ResponseEntity<String>> makeRequest(
            String url,
            HttpMethod method,
            String body,
            String token,
            String contentType,
            Map<String, String> params,
            Map<String, String> extraHeaders) {

        WebClient.RequestBodySpec requestSpec = webClient.method(method)
                .uri(url)
                .headers(headers -> {
                    if (token != null && !token.isEmpty()) {
                        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
                    }
                    if (contentType != null && !contentType.isEmpty()) {
                        headers.setContentType(MediaType.valueOf(contentType));
                    }
                    if (params != null) {
                        params.forEach(headers::set);
                    }
                    if (extraHeaders != null) {
                        extraHeaders.forEach(headers::set);
                    }
                });

        return requestSpec
                .bodyValue(body != null ? body : "")
                .retrieve()
                .toEntity(String.class)
                .onErrorResume(e -> {
                    System.err.println("Error en la petición: " + e.getMessage());
                    return Mono.just(ResponseEntity.internalServerError().body("Error en la petición"));
                });
    }
}
