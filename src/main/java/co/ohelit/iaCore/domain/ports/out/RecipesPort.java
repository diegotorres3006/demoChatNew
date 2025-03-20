package co.ohelit.iaCore.domain.ports.out;

import co.ohelit.iaCore.domain.models.Recipe;

import java.util.List;
import java.util.Map;

public interface RecipesPort {
    List<Recipe> getRecipes(String filterName, String filterValue, int pageSize);
    Map<String, Object> findStepByNumber(List<Map<String, Object>> yamlList, int targetStepNumber);
}
