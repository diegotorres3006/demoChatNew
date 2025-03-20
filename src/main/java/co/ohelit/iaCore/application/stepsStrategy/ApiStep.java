package co.ohelit.iaCore.application.stepsStrategy;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ApiStep implements Steps {

    @Override
    public void ejecutar(Map<String, Object> step) {
        System.out.println("DESDE API STEP: Ejecuto await QuysquaRepository.apiJsonQuys(JSON.parse(JSON.stringify(paso)), messageOrigin, hash)");
    }
}
