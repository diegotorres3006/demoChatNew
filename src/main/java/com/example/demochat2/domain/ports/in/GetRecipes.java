package com.example.demochat2.domain.ports.in;

import com.example.demochat2.domain.models.Recipe;

import java.util.List;

public interface GetRecipes {
    List<Recipe> get20Recipes();
}
