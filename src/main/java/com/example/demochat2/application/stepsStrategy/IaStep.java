package com.example.demochat2.application.stepsStrategy;

import com.example.demochat2.domain.models.Recipe;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class IaStep implements Steps {
    @Override
    public void ejecutar(Map<String, Object> step) {
        System.out.println("DESDE IA STEP: Ejecuto receta IA, tengo que: concatenar el prompt, generar el mensaje final usuario:prompt, gestionar que tipo de ia usar");

        Map<String, Object> paso = (Map<String, Object>) step.get("steps");
        System.out.println(paso);

        Map<String, Object> parameters = (Map<String, Object>) paso.get("parameters");
        System.out.println("Tengo el prompt: " +  parameters.get("prompt"));
        //entrarPrimerElemento(yaml);

        if(paso.get("variableNumber")!=null){
            System.out.println("Se supone que tengo que guardar la respuesta del usuario");
        } else {
            System.out.println("No debo guardar nadota");
        }

    }
}
