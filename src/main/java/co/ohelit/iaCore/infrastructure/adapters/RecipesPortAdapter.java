package co.ohelit.iaCore.infrastructure.adapters;

import co.ohelit.iaCore.domain.models.Recipe;
import co.ohelit.iaCore.domain.ports.out.RecipesPort;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import co.ohelit.iaCore.utils.QuysUtils;
import co.ohelit.iaCore.utils.WebClientService;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
public class RecipesPortAdapter implements RecipesPort {

    //Id del objeto recetas
    final int recipesObject = 17421;

    private final QuysUtils quysUtils;
    private final WebClientService webClientService;


    @Autowired
    public RecipesPortAdapter(QuysUtils quysUtils,  WebClientService webClientService) {
        this.quysUtils = quysUtils;
        this.webClientService = webClientService;

    }

    @Override
    public Map<String, Object> findStepByNumber(List<Map<String, Object>> yamlList, int targetStepNumber) {
        // Metodo para encontrar el paso por número de step
        for (Map<String, Object> item : yamlList) {
            // Acceder al mapa 'steps' y verificar el 'stepNumber'
            Map<String, Object> steps = (Map<String, Object>) item.get("steps");
            Integer stepNumber = (Integer) steps.get("stepNumber");
            if (stepNumber != null && stepNumber == targetStepNumber) {
                return item;
            }
        }
        return null;
    }

    @Override
    public List<Recipe> getRecipes(String filterName, String filterValue, int pageSize) {
        String url = "https://quysqua.uat.ohelit.net/api/" + this.recipesObject +
                "/getalldata?WithRelations=false&page=1&size=" + pageSize + "&sort=1(asc)";
        String token =  quysUtils.getQuysToken();

        // Gener filtro
        Map<String, String> params = webClientService.buildFilterParams(filterName, filterValue);


        // Llamada a makeRequest pasando el filtro en params (y body nulo para GET)
        Mono<ResponseEntity<String>> responseMono = webClientService.makeRequest(
                url,
                HttpMethod.GET,
                null, // body es null para GET
                token,
                "application/json",
                params,   // Enviamos el filtro como parámetro
                null      // No hay headers extra
        );

        try {
            // Bloquear la ejecución hasta recibir el resultado
            ResponseEntity<String> responseEntity = responseMono.block();

            // Parsear la respuesta JSON
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseEntity.getBody());
            JsonNode dataNode = rootNode.path("data"); // Obtener el campo "data"

            if (dataNode.isEmpty()) {
                System.out.println("Error: Respuesta vacía o nula");
                return List.of();
            }

            // Convertir el campo "data" a una lista de listas (por cada receta)
            List<List<Object>> rawItems = objectMapper.readValue(dataNode.toString(),
                    new TypeReference<List<List<Object>>>() {});

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
            recipe.setId(((Integer) item.get(0)).longValue()); // ID: Convertir Integer a Long de forma segura
            recipe.setCode((String) item.get(1)); // String nombre
            recipe.setConfiguration((String) item.get(2)); // String configuración de pasos
            recipe.setDescription((String) item.get(3)); // String descripción
            recipe.setAutomation((String) item.get(4)); // String del crone

            return recipe;
        }).toList();
    }

}
