package com.example.demochat2.application.services;

import com.example.demochat2.domain.models.Recipe;
import com.example.demochat2.domain.ports.in.GetARecipe;
import com.example.demochat2.domain.ports.in.GetRecipes;
import com.example.demochat2.infrastructure.adapters.RecipesPortAdapter;

import java.util.List;

public class RecipesService implements GetRecipes, GetARecipe {

    private final RecipesPortAdapter recipesPortAdapter;

    public RecipesService(RecipesPortAdapter recipesPortAdapter){
        this.recipesPortAdapter = recipesPortAdapter;
    }

    @Override
    public List<Recipe> get20Recipes() {
        return recipesPortAdapter.get20Recipes();
    }

    @Override
    public Recipe getARecipe(Long id) {
        return recipesPortAdapter.getARecipe(id);
    }
}
