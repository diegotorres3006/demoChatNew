package com.example.demochat2.infrastructure.adapters;

import com.example.demochat2.domain.models.Recipe;
import com.example.demochat2.domain.ports.out.RecipesPort;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import com.example.demochat2.infrastructure.config.AppConfig;

import com.example.demochat2.Utils;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class RecipesPortAdapter implements RecipesPort {
    final int recipesObject = 17421;

    private final WebClient.Builder webClientBuilder;
    private final Utils utils;
    private final AppConfig appConfig;
    //Para obtener token
    private final String quysClientID;
    private final String quysClientSecret;
    private final String quysApiFetchUrl;

    @Autowired
    public RecipesPortAdapter(WebClient.Builder webClientBuilder, Utils utils, AppConfig appConfig) {
        this.webClientBuilder = webClientBuilder;
        this.utils = utils;
        this.appConfig = appConfig;

        this.quysClientID = appConfig.getQuysClientId();
        this.quysClientSecret = appConfig.getQuysClientSecret();
        this.quysApiFetchUrl = appConfig.getQuysApiFetchUrl();
    }

    @Override
    public List<Recipe> get20Recipes() {
        return getRecipes(recipesObject, null, null, 20) ;
    }

    @Override
    public Recipe getARecipe(Long id){
        List<Recipe> recipe = getRecipes(recipesObject, "id", id, 1);
        return recipe.get(0);
    }

    public List<Recipe> getRecipes(int objectId, String filterName, Long filterId, int pageSize) {
        String url = "https://quysqua.uat.ohelit.net/api/" + objectId + "/getalldata?WithRelations=false&page=1&size=" + pageSize + "&sort=1(asc)";
        String token2 = utils.getToken(this.quysClientID, this.quysClientSecret, this.quysApiFetchUrl).block();


        // Construcción del parámetro en formato JSON si se especifica el filtro
        String body = null;
        if (filterName != null && filterId != null) {
            body = "{\"data\":{\"" + filterName + "\":" + filterId + "}}";
        }

        // Llamada a `makeRequest`
        Mono<ResponseEntity<String>> responseMono = utils.makeRequest(
                url,
                HttpMethod.GET,
                body,
                token2,
                "application/json",
                null, // No hay parámetros
                null  // No hay headers extra
        );

        try {
            // bloquear la ejecución hasta recibir el resultado
            ResponseEntity<String> responseEntity = responseMono.block();

            if (responseEntity == null || responseEntity.getBody() == null) {
                System.out.println("Error: Respuesta vacía o nula");
                return List.of();
            }

            // Parsear la respuesta JSON
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseEntity.getBody());
            JsonNode dataNode = rootNode.path("data"); // Obtener el campo "data"

            // Convertir el campo "data" a una lista de listas (por cada receta)
            List<List<Object>> rawItems = objectMapper.readValue(dataNode.toString(), new TypeReference<List<List<Object>>>() {});

            // Mapeamos cada item (que es un array) a un objeto Recipe
            return mapRawItemsToRecipes(rawItems);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al procesar la respuesta JSON");
            return List.of();
        }
    }

    private List<Recipe> mapRawItemsToRecipes(List<List<Object>> rawItems) {
        return rawItems.stream().map(item -> {
            Recipe recipe = new Recipe();
            // Cambiar la conversión del ID de Integer a Long
            recipe.setId(((Integer) item.get(0)).longValue()); // Convertir Integer a Long de forma segura

            recipe.setCode((String) item.get(1)); // Suponiendo que el segundo valor es el nombre
            recipe.setConfiguration((String) item.get(2)); // El tercer valor parece ser una configuración de pasos
            recipe.setDescription((String) item.get(3)); // Cuarto valor es la descripción
            recipe.setAutomation((String) item.get(4)); // El quinto valor parece ser nulo o algún valor adicional (puedes omitir si no es necesario)

            return recipe;
        }).toList();
    }

}
