package com.example.demochat2.application.stepsStrategy;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class WhatsAppStep implements Steps {

    @Override
    public void ejecutar(Map<String, Object> step) {
        System.out.println("DESDE WHATSPAPP STEP: Ejecuto  await MessageHandler.useSendMessage(messageOrigin, paso.steps);");

        //String yaml = baseRecipe.getConfiguration();
        //System.out.println("yaml base de getConfigutaion " + yaml);

        Map<String, Object> paso = (Map<String, Object>) step.get("steps");
        System.out.println(paso);

        Map<String, Object> parameters = (Map<String, Object>) paso.get("parameters");
        System.out.println("Debo ir al api wstp y enviar: "+parameters.get("message"));
        //entrarPrimerElemento(yaml);

        if(paso.get("variableNumber")!=null){
            System.out.println("Se supone que tengo que guardar la respuesta del usuario");
        } else {
            System.out.println("No debo guardar nadota");
        }

    }

    /*public void entrarPrimerElemento ( String yaml ){

        try {
            List<Map<String, Object>> recipeJson = yamlService.yamlToJson(yaml);

            // Acceder al primer elemento de la lista
            Map<String, Object> firstItem = recipeJson.get(0);

            // Acceder a la clave 'steps' en el primer elemento
            Map<String, Object> steps = (Map<String, Object>) firstItem.get("steps");

            // Acceder a 'name' dentro de 'steps'
            String name = (String) steps.get("name");
            System.out.println("steps.name: " + name);  // Debería imprimir: ENROLLMENT_ID

            // Acceder a 'parameters' dentro de 'steps' y luego a 'message'
            Map<String, Object> parameters = (Map<String, Object>) steps.get("parameters");
            String message = (String) parameters.get("message");
            System.out.println("steps.parameters.message: " + message);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }*/

}
