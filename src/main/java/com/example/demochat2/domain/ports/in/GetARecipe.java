package com.example.demochat2.domain.ports.in;

import com.example.demochat2.domain.models.Recipe;

public interface GetARecipe {
    Recipe getARecipe(Long id);
}
