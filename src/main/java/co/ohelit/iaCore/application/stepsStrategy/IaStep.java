package co.ohelit.iaCore.application.stepsStrategy;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class IaStep implements Steps {
    @Override
    public void ejecutar(Map<String, Object> steps) {
        System.out.println("DESDE IA STEP:");

        /*Este paso debe extraer información del step para enviar un prompt a una IA
        ya sea GPT, DeepSeek u otro

        A continuación se explican todos los atributos de un paso:
        name: HIGH_NOTE
        type: IA
        variableNumber: 5
        model: openAi
        parameters:
          prompt: "prompt"
          context:
            - 4
        stepNumber: 3
        nextStep: 4
        */

        //Obtener step
        Map<String, Object> step = (Map<String, Object>) steps.get("steps");

        Integer variableNumber = (Integer) step.get("variableNumber");
        String model = (String) step.get("model");
        Integer nextStep = (Integer) step.get("nextStep");

        Map<String, Object> parameters = (Map<String, Object>) step.get("parameters");
        String prompt = (String) parameters.get("prompt");
        List<Integer> context = (List<Integer>) parameters.get("context");


        System.out.println(variableNumber +" "+ model +" "+ nextStep +" "+ prompt +" "+ context);


        if(variableNumber != null){
            System.out.println("Se supone que tengo que guardar la respuesta del usuario");
        } else {
            System.out.println("No debo guardar nadota");
        }

    }
}
