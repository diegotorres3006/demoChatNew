package com.example.demochat2.application.stepsStrategy;

import org.springframework.stereotype.Component;

@Component
public class ApiStep implements Steps {

    @Override
    public void ejecutar() {
        System.out.println("Ejecuto await QuysquaRepository.apiJsonQuys(JSON.parse(JSON.stringify(paso)), messageOrigin, hash)");
    }
}
