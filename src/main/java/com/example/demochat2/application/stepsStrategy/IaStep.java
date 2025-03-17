package com.example.demochat2.application.stepsStrategy;

import org.springframework.stereotype.Component;

@Component
public class IaStep implements Steps {
    @Override
    public void ejecutar() {
        System.out.println("Ejecuto receta IA, tengo que: concatenar el prompt, generar el mensaje final usuario:prompt, gestionar que tipo de ia usar");
    }
}
