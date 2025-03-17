package com.example.demochat2.application.usecases;

import com.example.demochat2.domain.models.Recipe;
import com.example.demochat2.domain.ports.in.GetRecipes;
import com.example.demochat2.domain.ports.out.RecipesPort;

import java.util.List;

public class GetRecipesUseCaseImp implements GetRecipes {

    private final RecipesPort recipesPort;

    public GetRecipesUseCaseImp(RecipesPort recipesPort){
        this.recipesPort = recipesPort;
    }

    @Override
    public List<Recipe> get20Recipes() {
        return recipesPort.get20Recipes();
    }
}
