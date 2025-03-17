package com.example.demochat2.infrastructure.controllers;

import com.example.demochat2.application.services.RecipesService;
import com.example.demochat2.domain.models.Recipe;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RecipesController {

    RecipesService recipesService;

    public RecipesController(RecipesService recipesService){
        this.recipesService = recipesService;
    }

    @GetMapping("/recipes")
    public ResponseEntity<List<Recipe>> getAllRecipes(){
        List<Recipe> recipes = recipesService.get20Recipes();
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @GetMapping("/recipes/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Long id){
        Recipe recipe = recipesService.getARecipe(id);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

}
