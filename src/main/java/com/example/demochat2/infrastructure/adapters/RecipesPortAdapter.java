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
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJWaUtxWjdSTm5ndE1oVDlFQTlrSUNPa3h6OUk4Z083ZkFuVWZuRnowY3FNIn0.eyJleHAiOjE3NDIyMzUzOTksImlhdCI6MTc0MjIyODE5OSwiYXV0aF90aW1lIjoxNzQyMjI4MTk0LCJqdGkiOiI1YzE1MjUxNy02ODZiLTQ1NTAtOWUyNC0zODg2YzI1Mjk3MzAiLCJpc3MiOiJodHRwczovL2F1dGgudWF0Lm9oZWxpdC5uZXQvYXV0aC9yZWFsbXMvdW5pbW9uc2VycmF0ZSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiIzYzQ1ZGNkNy03MGFkLTRkYTUtYjlmNS04MGJjYmQ3ZjM4MTciLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJ3ZWJfYXBwIiwic2Vzc2lvbl9zdGF0ZSI6IjVjYWY3YWVmLTY1NzMtNDc3MS04YmFhLTY4ZDZjYjJlMDFkNiIsImFsbG93ZWQtb3JpZ2lucyI6WyIqIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJST0xFX0FETUlOX1VOSU1PTiIsIlJPTEVfVVNFUiIsIm9mZmxpbmVfYWNjZXNzIiwiUk9MRV9DSFlfUkVQT19BRE1JTiIsImRlZmF1bHQtcm9sZXMtdW5pbW9uc2VycmF0ZSIsIlJPTEVfQURNSU4iLCJST0xFX1FZTl9VU0VSIiwidW1hX2F1dGhvcml6YXRpb24iLCJST0xFX0NIWV9ET0NVX0FETUlOIiwiUk9MRV9PQkNfQURNSU4iLCJST0xFX1FVWV9BUFBfU1RVXzAxIiwiUk9MRV9DSFlfSU5URV9BRE1JTiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsInNpZCI6IjVjYWY3YWVmLTY1NzMtNDc3MS04YmFhLTY4ZDZjYjJlMDFkNiIsInRlbmFudF9pZCI6MiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJyb2xlcyI6WyJST0xFX0FETUlOX1VOSU1PTiIsIlJPTEVfVVNFUiIsIm9mZmxpbmVfYWNjZXNzIiwiUk9MRV9DSFlfUkVQT19BRE1JTiIsImRlZmF1bHQtcm9sZXMtdW5pbW9uc2VycmF0ZSIsIlJPTEVfQURNSU4iLCJST0xFX1FZTl9VU0VSIiwidW1hX2F1dGhvcml6YXRpb24iLCJST0xFX0NIWV9ET0NVX0FETUlOIiwiUk9MRV9PQkNfQURNSU4iLCJST0xFX1FVWV9BUFBfU1RVXzAxIiwiUk9MRV9DSFlfSU5URV9BRE1JTiIsIlJPTEVfQURNSU5fVU5JTU9OIiwiUk9MRV9VU0VSIiwib2ZmbGluZV9hY2Nlc3MiLCJST0xFX0NIWV9SRVBPX0FETUlOIiwiZGVmYXVsdC1yb2xlcy11bmltb25zZXJyYXRlIiwiUk9MRV9BRE1JTiIsIlJPTEVfUVlOX1VTRVIiLCJ1bWFfYXV0aG9yaXphdGlvbiIsIlJPTEVfQ0hZX0RPQ1VfQURNSU4iLCJST0xFX09CQ19BRE1JTiIsIlJPTEVfUVVZX0FQUF9TVFVfMDEiLCJST0xFX0NIWV9JTlRFX0FETUlOIl0sIm5hbWUiOiJKYWlybyBBbGVqYW5kcm8gUmV5ZXMgRHVhcnRlIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiamFsZWphbmRyb3JleWVzQHVuaW1vbnNlcnJhdGUuZWR1LmNvIiwiZ2l2ZW5fbmFtZSI6IkphaXJvIEFsZWphbmRybyIsImZhbWlseV9uYW1lIjoiUmV5ZXMgRHVhcnRlIiwiZW1haWwiOiJqYWxlamFuZHJvcmV5ZXNAdW5pbW9uc2VycmF0ZS5lZHUuY28ifQ.TjwoIhzPH9UGvzhDj63kHjVzGMwgqdR0u7Rmz0EiP8WcOCo5z8fyLtJQw_k7VlgM5NAoXBP6q5riHdErO1sF1VpDg83nmUqGfxA0rYrxQATaCTGMECiQlldL-U0CiC-b4yrTIPG1pvq97czV9D5Yu0_b4lfvmlerBItNFVrEWHPgXUVIbU26nyr0QWb8rUmpuTBdcZ3-yt2eS6b5VRRFqpKCXV7jjpBUkvXwd-W9oVPwvJ6NFL4cFndKBbgRFkIF_4pMThAMEJUaQhlx-DjTQGTpyNwv4KoFY7lo8xjBY8L9gQJXH9mR0c-MFiZotBu-ok0jZQwpjxmZ2_wqArDnIw";
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
