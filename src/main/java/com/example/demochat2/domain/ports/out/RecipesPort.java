package com.example.demochat2.domain.ports.out;

import com.example.demochat2.domain.models.Recipe;

import java.util.List;

public interface RecipesPort {
    List<Recipe> get20Recipes();
    Recipe getARecipe(Long id);
}
