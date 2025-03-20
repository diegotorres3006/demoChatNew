package co.ohelit.iaCore.application.services;

import co.ohelit.iaCore.domain.models.Recipe;
import co.ohelit.iaCore.domain.ports.in.GetStep;
import co.ohelit.iaCore.domain.ports.in.GetRecipes;
import co.ohelit.iaCore.infrastructure.adapters.RecipesPortAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RecipesService implements GetRecipes, GetStep {

    private final RecipesPortAdapter recipesPortAdapter;

    @Autowired
    public RecipesService(RecipesPortAdapter recipesPortAdapter){
        this.recipesPortAdapter = recipesPortAdapter;
    }

    @Override
    public List<Recipe> getRecipes(String filterName, String filterValue, int pageSize) {
        return recipesPortAdapter.getRecipes(filterName, filterValue, pageSize);
    }

    @Override
    public Map<String, Object> findStepByNumber (List<Map<String, Object>> yamlList, int targetStepNumber){
        return recipesPortAdapter.findStepByNumber(yamlList, targetStepNumber);
    }

}
