package co.ohelit.iaCore.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
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

    public Map<String, String> buildFilterParams(String filterName, String filterValue) {
        Map<String, String> params = new HashMap<>();
        if (filterName != null && filterValue != null) {
            String filterJson = "{\"data\":{\"" + filterName + "\":\"" + filterValue + "\"}}";
            params.put("param", filterJson);
        }
        return params; // Mapa vacío si no hay filtro
    }

    public Map<String, String> buildFilterParams(List<String> filterNames, List<String> filterValues) {
        Map<String, String> params = new HashMap<>();

        if (filterNames != null && filterValues != null && filterNames.size() == filterValues.size()) {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode filterJson = objectMapper.createObjectNode();
            ObjectNode dataNode = filterJson.putObject("data");

            for (int i = 0; i < filterNames.size(); i++) {
                dataNode.put(filterNames.get(i), filterValues.get(i));
            }

            try {
                params.put("param", objectMapper.writeValueAsString(filterJson));
            } catch (JsonProcessingException e) {
                System.err.println("Error construyendo JSON de filtros: " + e.getMessage());
            }
        }

        return params;
    }



}
