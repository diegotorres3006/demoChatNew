package co.ohelit.iaCore.application.repositories;

import co.ohelit.iaCore.application.services.RecipesService;
import co.ohelit.iaCore.domain.models.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecipesRepository {

    private final RecipesService recipesService;

    @Autowired
    public RecipesRepository(RecipesService recipesService){
        this.recipesService = recipesService;
    }

    /*Toda logica aqui*/

    List<Recipe> todasRecetas = new ArrayList<>();

    public String generateMenu(){
        List<Recipe> recipes = new ArrayList<>(this.recipesService.getRecipes(null, null, 1));

        if ((todasRecetas == null) || (recipes.size() > todasRecetas.size())){
            todasRecetas = recipes;
        }

        StringBuilder sb = new StringBuilder("Elija la opción que desea realizar:\n\n");
        recipes.forEach(recipe -> sb.append("*ID:* ").append(recipe.getId()).append(", *Usabilidad/Descripción:* ").append(recipe.getDescription()).append("\n"));
        sb.append("\n _*Si usted conoce el ID de una receta que no aparezca en la lista puede escribirlo*_ \n");
        return sb.toString();
    }

    public boolean searchRecipe(Long idRecipe){
        boolean recipeExists = this.todasRecetas.stream().anyMatch(recipe -> recipe.getId().equals(idRecipe));
        if (!recipeExists){
            List<Recipe> tempList = new ArrayList<>(this.recipesService.getRecipes("id", Long.toString(idRecipe), 1));
            if (!tempList.isEmpty()){
                this.todasRecetas.addAll(tempList);
                System.out.println("ENRTRÉ A AGREGAR");
                this.todasRecetas.forEach(recipe -> System.out.println(recipe.getId() + " "+recipe.getCode()));
            }
        }
        return recipeExists;
    }

}
