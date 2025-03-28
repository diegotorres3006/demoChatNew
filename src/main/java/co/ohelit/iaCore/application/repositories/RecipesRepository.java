package co.ohelit.iaCore.application.repositories;

import co.ohelit.iaCore.application.services.RecipesService;
import co.ohelit.iaCore.application.services.YamlService;
import co.ohelit.iaCore.application.stepsStrategy.ApiStep;
import co.ohelit.iaCore.application.stepsStrategy.IaStep;
import co.ohelit.iaCore.application.stepsStrategy.MessageStep;
import co.ohelit.iaCore.application.stepsStrategy.Steps;
import co.ohelit.iaCore.domain.models.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class RecipesRepository {

    private final RecipesService recipesService;
    private final YamlService yamlService;
    private final MessageStep messageStep;
    private final IaStep iaStep;
    private final ApiStep apiStep;
    Steps stepsInterface;

    @Autowired
    public RecipesRepository(RecipesService recipesService, YamlService yamlService,
                             MessageStep messageStep, IaStep iaStep, ApiStep apiStep){
        this.recipesService = recipesService;
        this.yamlService = yamlService;
        this.messageStep = messageStep;
        this.iaStep = iaStep;
        this.apiStep = apiStep;
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

    public Recipe searchRecipe(Long idRecipe){

        Optional<Recipe> recipe = this.todasRecetas.stream()
                .filter(rp -> rp.getId().equals(idRecipe))
                .findFirst();

        Recipe recipeExists = recipe.orElse(null);

        if (recipeExists==null){
            //Si la receta no se encontro, buscarla en quysqua
            List<Recipe> tempList = new ArrayList<>(this.recipesService.getRecipes("id", Long.toString(idRecipe), 1));
            if (!tempList.isEmpty()){
                //Se agrega la receta al listado local
                this.todasRecetas.addAll(tempList);
                System.out.println("ENRTRÉ A AGREGAR");
                this.todasRecetas.forEach(rp -> System.out.println(rp.getId() + " "+rp.getCode()));
                //Como ya se guardo la receta en el listado, vovler a llamar el metodo
                //Esto entrara directamente al else y retorna la receta entera
                return searchRecipe(idRecipe);
            } else {
                //Si no se encuentra la receta en quysqua, enviar null
                return null;
            }
        } else {
            //Si la receta se encontro, retornarla
            return recipe.get();
        }
    }

    public void iniciarReceta(Recipe recipe, String origin){
        if (recipe!=null){
            System.out.println("Iniciare la receta " + recipe.getId());
            String steps = recipe.getConfiguration();
            System.out.println("Encontre estos pasos: " + steps);

            List<Map<String, Object>> stepsJson = this.yamlService.yamlToJson(steps);
            Integer currentStep = 1;

            while (currentStep != null){
                System.out.println("Empezare el bucle while, paso actual: "+ currentStep);
                Map<String, Object> step = (Map<String, Object>) this.recipesService.findStepByNumber(stepsJson, currentStep).get("steps");
                System.out.println("Busque el paso " + currentStep+ " y encontre: "+ step);

                String type = (String) step.get("type");

                switch (type){
                    case "WHATSAPP_MESSAGE":
                        stepsInterface = this.messageStep;
                        stepsInterface.ejecutar(step, origin);
                        break;
                    case "API":
                        stepsInterface = this.apiStep;
                        stepsInterface.ejecutar(step, origin);
                        break;
                    case "IA":
                        stepsInterface = this.iaStep;
                        stepsInterface.ejecutar(step, origin);
                        break;
                    default: break;
                }

                //Actualizacion del paso actual
                currentStep = (Integer) step.get("nextStep");
                System.out.println("Cambio de paso " + currentStep);

            }

        }
    }

}
