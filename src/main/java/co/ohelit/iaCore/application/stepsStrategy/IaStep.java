package co.ohelit.iaCore.application.stepsStrategy;

import co.ohelit.iaCore.application.services.OpenAiChatService;
import co.ohelit.iaCore.infrastructure.adapters.OpenAiAssistant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class IaStep implements Steps {

    private final OpenAiChatService openAiChatService;

    public IaStep(OpenAiChatService openAiChatService){
        this.openAiChatService = openAiChatService;
    }

    @Override
    public void ejecutar(Map<String, Object> step, String origin) {
        System.out.println("DESDE IA STEP: " + step);

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

        Integer variableNumber = (Integer) step.get("variableNumber");
        String model = (String) step.get("model");
        Integer nextStep = (Integer) step.get("nextStep");

        Map<String, Object> parameters = (Map<String, Object>) step.get("parameters");
        String prompt = (String) parameters.get("prompt");
        List<Integer> context = (List<Integer>) parameters.get("context");


        System.out.println(variableNumber +" "+ model +" "+ nextStep +" "+ prompt +" "+ context);

        System.out.println(this.openAiChatService.chat(prompt));


        if(variableNumber != null){
            System.out.println("Se supone que tengo que guardar la respuesta del usuario");
        } else {
            System.out.println("No debo guardar nadota");
        }

    }
}
