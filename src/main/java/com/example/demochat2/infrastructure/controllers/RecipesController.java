package com.example.demochat2.infrastructure.controllers;

import com.example.demochat2.application.services.RecipesService;
import com.example.demochat2.application.services.YamlService;
import com.example.demochat2.domain.models.Recipe;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RecipesController {

    RecipesService recipesService;
    YamlService yamlService;

    public RecipesController(RecipesService recipesService,
        YamlService yamlService
    ){
        this.recipesService = recipesService;
        this.yamlService = yamlService;
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

    @GetMapping("/json")
    public List<Map<String, Object>> yamlToJson() throws JsonProcessingException {
        return yamlService.yamlToJson("- steps:\n" +
                "    name: ENROLLMENT_ID\n" +
                "    type: WHATSAPP_MESSAGE\n" +
                "    saveUserResponse: true\n" +
                "    variableNumber: 1\n" +
                "    expectedDataType: number\n" +
                "    parameters:\n" +
                "      message: \"Por favor ingresa el número de matrícula del estudiante\"\n" +
                "    stepNumber: 1\n" +
                "    nextStep: 2\n" +
                "- steps:\n" +
                "    name: NOTES_CONSULT\n" +
                "    type: API\n" +
                "    parameters:\n" +
                "      method: POST\n" +
                "      apiLink: \"https://quysqua.uat.ohelit.net/api/13588/create\"\n" +
                "      body:\n" +
                "          minimum_grade: 1\n" +
                "          maximum_grade: \n" +
                "            - localVariable: 1\n" +
                "          id_acdmcol_institutional_performance: 2\n" +
                "          year: '2024-03-25T16:23:00'\n" +
                "    stepNumber: null\n" +
                "    nextStep: null\n" +
                "- steps:\n" +
                "    name: NOTES_CONSULT\n" +
                "    type: API\n" +
                "    variableNumber: 4\n" +
                "    parameters:\n" +
                "      method: GET\n" +
                "      apiLink: \"https://quysqua.uat.ohelit.net/api/17240/getalldata?WithRelations=false&page=1&size=20&sort=1(desc)\"\n" +
                "      filter:\n" +
                "        filterName: id\n" +
                "        localVariableFilter: 1\n" +
                "        filterId: \"\"\n" +
                "    stepNumber: 2\n" +
                "    nextStep: 3\n" +
                "- steps:\n" +
                "    name: HIGH_NOTE\n" +
                "    type: IA\n" +
                "    variableNumber: 5\n" +
                "    model: openAi\n" +
                "    parameters:\n" +
                "      prompt: \"Eres un analista de notas académicas y a partir de la información anterior debes hacer un análisis de las mejores notas del estudiante y mostrarlas al usuario. Ten presente que el 0 cuenta como una nota. Responde en español.\"\n" +
                "      context:\n" +
                "        - 4\n" +
                "    stepNumber: 3\n" +
                "    nextStep: 4\n" +
                "- steps:\n" +
                "    name: MINOR_NOTE\n" +
                "    type: IA\n" +
                "    variableNumber: 6\n" +
                "    model: openAi\n" +
                "    parameters:\n" +
                "      prompt: \"Eres un analista de notas académicas y a partir de la información anterior debes hacer un análisis de las menores notas del estudiante y mostrarlas al usuario. Ten presente que el 0 cuenta como una nota. Responde en español.\"\n" +
                "      context:\n" +
                "        - 4\n" +
                "    stepNumber: 4\n" +
                "    nextStep: 5\n" +
                "- steps:\n" +
                "    name: ACTION_PLAN\n" +
                "    type: IA\n" +
                "    variableNumber: 7\n" +
                "    model: openAi\n" +
                "    parameters:\n" +
                "      prompt: \"Eres un analista de notas académicas y debes buscar todas las materias cuya nota final sea menor a la dada en el campo nota aprobatoria y a cada una de ellas darle un plan de acción de mejora para la materia en base a los temas dados en el campo logros_corte. Ten presente que el 0 cuenta como una nota. Responde en español.\"\n" +
                "      context:\n" +
                "        - 4\n" +
                "    stepNumber: 5\n" +
                "    nextStep: null");
    }

}
