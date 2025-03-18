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
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJWaUtxWjdSTm5ndE1oVDlFQTlrSUNPa3h6OUk4Z083ZkFuVWZuRnowY3FNIn0.eyJleHAiOjE3NDIyODEzNjcsImlhdCI6MTc0MjI3NDE2NywiYXV0aF90aW1lIjoxNzQyMjc0MTY2LCJqdGkiOiI0YTRkNWVkMS1jNmU2LTQ2NjMtYjE0Yi0wMmY2NTYyZTllNTMiLCJpc3MiOiJodHRwczovL2F1dGgudWF0Lm9oZWxpdC5uZXQvYXV0aC9yZWFsbXMvdW5pbW9uc2VycmF0ZSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI4YTlhMTRjOC0xYTE4LTQ0NmMtOWNhNS05MzQ1MDFmYjk1OTAiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJ3ZWJfYXBwIiwic2Vzc2lvbl9zdGF0ZSI6IjkyNGIzNTUxLTdlZTctNDRmOS05M2VmLTExYmY2OGVlZjdkNSIsImFsbG93ZWQtb3JpZ2lucyI6WyIqIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJST0xFX0FETUlOX1VOSU1PTiIsIlJPTEVfVVNFUiIsIm9mZmxpbmVfYWNjZXNzIiwiUk9MRV9DSFlfUkVQT19BRE1JTiIsIlJPTEVfQURNSU4iLCJST0xFX0NIWV9ET0NVX0FETUlOIiwiUk9MRV9PQkNfQURNSU4iLCJST0xFX0NIWV9JTlRFX0FETUlOIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwic2lkIjoiOTI0YjM1NTEtN2VlNy00NGY5LTkzZWYtMTFiZjY4ZWVmN2Q1IiwidGVuYW50X2lkIjoyLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsInJvbGVzIjpbIlJPTEVfQURNSU5fVU5JTU9OIiwiUk9MRV9VU0VSIiwib2ZmbGluZV9hY2Nlc3MiLCJST0xFX0NIWV9SRVBPX0FETUlOIiwiUk9MRV9BRE1JTiIsIlJPTEVfQ0hZX0RPQ1VfQURNSU4iLCJST0xFX09CQ19BRE1JTiIsIlJPTEVfQ0hZX0lOVEVfQURNSU4iLCJST0xFX0FETUlOX1VOSU1PTiIsIlJPTEVfVVNFUiIsIm9mZmxpbmVfYWNjZXNzIiwiUk9MRV9DSFlfUkVQT19BRE1JTiIsIlJPTEVfQURNSU4iLCJST0xFX0NIWV9ET0NVX0FETUlOIiwiUk9MRV9PQkNfQURNSU4iLCJST0xFX0NIWV9JTlRFX0FETUlOIl0sIm5hbWUiOiJEaWVnbyBBbmRyZXMgVG9ycmVzIFJpdmVyb3MiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJkYXRvcnJlc0B1bmltb25zZXJyYXRlLmVkdS5jbyIsImdpdmVuX25hbWUiOiJEaWVnbyBBbmRyZXMiLCJmYW1pbHlfbmFtZSI6IlRvcnJlcyBSaXZlcm9zIiwiZW1haWwiOiJkYXRvcnJlc0B1bmltb25zZXJyYXRlLmVkdS5jbyJ9.m3grhbWcv4EGa5ipf2Sfjc8Bxau4IhHXRsw8g75NlK-V7j2Xw4QRBam9RjxbSqDM5pmX8olqmTGeOzfH2Z0fioHnemHRSNV7UqnNj-hG6TVFe0WbUF0jhrPChlFDKf1Lu0vLm2Px2hTXscgCZbsS9tLmrjRgP3icP0QUyrceHllZpcWGo8YakoKL0Cy96-Tet5aXhVO0CWYzBb4iAi7FwNrNAEyoDnpl46Glo1LGdvb7m1ipbU8trFbY4K-vX5GOU6WciIpjlrsciO6EHf_4LFmH6SZTZjcO0OldQ3jG9IvQ2vFlQyE-NK48TbKk-kKN7yRPYQHbwkVuDdIWbkw0Cw";
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
