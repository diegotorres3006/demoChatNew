package com.example.demochat2.application.stepsStrategy;

import com.example.demochat2.domain.models.Recipe;

import java.util.Map;

public interface Steps {
    public void ejecutar(Map<String, Object> step);
}
