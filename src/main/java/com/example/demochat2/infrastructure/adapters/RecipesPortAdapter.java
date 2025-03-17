package com.example.demochat2.infrastructure.adapters;

import com.example.demochat2.domain.models.Recipe;
import com.example.demochat2.domain.ports.out.RecipesPort;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class RecipesPortAdapter implements RecipesPort {
    final int recipesObject = 17421;

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public RecipesPortAdapter(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
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
        String url = "https://quysqua.uat.ohelit.net/api/"+objectId+"/getalldata?WithRelations=false&page=1&size="+pageSize+"&sort=1(asc)";
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJWaUtxWjdSTm5ndE1oVDlFQTlrSUNPa3h6OUk4Z083ZkFuVWZuRnowY3FNIn0.eyJleHAiOjE3NDIyMzg1NjEsImlhdCI6MTc0MjIzMTM2MSwiYXV0aF90aW1lIjoxNzQyMjI4MTk0LCJqdGkiOiJhNGI1YjJjNi1iYzg5LTQ0YjMtYjgzNS1kNDA0OThiMTA3ZTUiLCJpc3MiOiJodHRwczovL2F1dGgudWF0Lm9oZWxpdC5uZXQvYXV0aC9yZWFsbXMvdW5pbW9uc2VycmF0ZSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiIzYzQ1ZGNkNy03MGFkLTRkYTUtYjlmNS04MGJjYmQ3ZjM4MTciLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJ3ZWJfYXBwIiwic2Vzc2lvbl9zdGF0ZSI6IjVjYWY3YWVmLTY1NzMtNDc3MS04YmFhLTY4ZDZjYjJlMDFkNiIsImFsbG93ZWQtb3JpZ2lucyI6WyIqIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJST0xFX0FETUlOX1VOSU1PTiIsIlJPTEVfVVNFUiIsIm9mZmxpbmVfYWNjZXNzIiwiUk9MRV9DSFlfUkVQT19BRE1JTiIsImRlZmF1bHQtcm9sZXMtdW5pbW9uc2VycmF0ZSIsIlJPTEVfQURNSU4iLCJST0xFX1FZTl9VU0VSIiwidW1hX2F1dGhvcml6YXRpb24iLCJST0xFX0NIWV9ET0NVX0FETUlOIiwiUk9MRV9PQkNfQURNSU4iLCJST0xFX1FVWV9BUFBfU1RVXzAxIiwiUk9MRV9DSFlfSU5URV9BRE1JTiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUiLCJzaWQiOiI1Y2FmN2FlZi02NTczLTQ3NzEtOGJhYS02OGQ2Y2IyZTAxZDYiLCJ0ZW5hbnRfaWQiOjIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwicm9sZXMiOlsiUk9MRV9BRE1JTl9VTklNT04iLCJST0xFX1VTRVIiLCJvZmZsaW5lX2FjY2VzcyIsIlJPTEVfQ0hZX1JFUE9fQURNSU4iLCJkZWZhdWx0LXJvbGVzLXVuaW1vbnNlcnJhdGUiLCJST0xFX0FETUlOIiwiUk9MRV9RWU5fVVNFUiIsInVtYV9hdXRob3JpemF0aW9uIiwiUk9MRV9DSFlfRE9DVV9BRE1JTiIsIlJPTEVfT0JDX0FETUlOIiwiUk9MRV9RVVlfQVBQX1NUVV8wMSIsIlJPTEVfQ0hZX0lOVEVfQURNSU4iLCJST0xFX0FETUlOX1VOSU1PTiIsIlJPTEVfVVNFUiIsIm9mZmxpbmVfYWNjZXNzIiwiUk9MRV9DSFlfUkVQT19BRE1JTiIsImRlZmF1bHQtcm9sZXMtdW5pbW9uc2VycmF0ZSIsIlJPTEVfQURNSU4iLCJST0xFX1FZTl9VU0VSIiwidW1hX2F1dGhvcml6YXRpb24iLCJST0xFX0NIWV9ET0NVX0FETUlOIiwiUk9MRV9PQkNfQURNSU4iLCJST0xFX1FVWV9BUFBfU1RVXzAxIiwiUk9MRV9DSFlfSU5URV9BRE1JTiJdLCJuYW1lIjoiSmFpcm8gQWxlamFuZHJvIFJleWVzIER1YXJ0ZSIsInByZWZlcnJlZF91c2VybmFtZSI6ImphbGVqYW5kcm9yZXllc0B1bmltb25zZXJyYXRlLmVkdS5jbyIsImdpdmVuX25hbWUiOiJKYWlybyBBbGVqYW5kcm8iLCJmYW1pbHlfbmFtZSI6IlJleWVzIER1YXJ0ZSIsImVtYWlsIjoiamFsZWphbmRyb3JleWVzQHVuaW1vbnNlcnJhdGUuZWR1LmNvIn0.gb0v4OhZ9qfw-OndjZN317KqTXfWIn28l0k7ZPaGKTz9AFhZGh1ERoBYm-dEBsUz-x1udZAljBtInpyvcYXWNiqNoXnNI8fxw8MZ5waTQApajPMmdHzdbFd6rZ0Z7K5wOyUWmbX5lrVP4fXExBZH9A4OWkoRZ5LaSJGh5iaq5YChsCD7cLh40ogh2oPwEPNLX-_uDSr_q_rN8-5xWPfP2nwhvhUZK3Q5-ypHAuNqMnuLg3pDGvMw_x-y-p7KLOw-Mamd-IFZtatWoHMHu1ANWOTY1mEaXZkFe7ybX0UQ2h9-tXURKzxuP6pRDS0BjjqpYmmya13UAUeKycCilsa54g";
        WebClient webClient = webClientBuilder.baseUrl(url).build();

        String param = null;

        if(filterName != null && filterId != null){
            param = "{'data':{"+filterName+":"+filterId+"}}";
        }

        try {
            // Realizamos la solicitud GET y obtenemos la respuesta
            String response = webClient.get()
                    .uri("")
                    .header("Authorization", "Bearer " + token)
                    .header("param", param)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); // Bloqueamos hasta obtener la respuesta

            // Parseamos la respuesta JSON
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response); // Leer el JSON como un árbol
            JsonNode dataNode = rootNode.path("data"); // Obtener el campo "data" que contiene el array

            // Convertir el campo "data" a una lista de listas (por cada receta)
            List<List<Object>> rawItems = objectMapper.readValue(dataNode.toString(), new TypeReference<List<List<Object>>>() {});

            // Mapeamos cada item (que es un array) a un objeto Recipe
            List<Recipe> recipes = mapRawItemsToRecipes(rawItems);
            return recipes;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println( "Error al procesar la respuesta JSON");
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
