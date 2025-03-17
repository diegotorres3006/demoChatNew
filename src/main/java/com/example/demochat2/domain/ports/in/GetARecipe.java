package com.example.demochat2.domain.ports.in;

import com.example.demochat2.domain.models.Recipe;

public interface GetARecipe {
    com.example.demochat2.domain.models.Recipe getARecipe(Long id);
}
