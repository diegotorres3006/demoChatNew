package co.ohelit.iaCore.application.stepsStrategy;

import co.ohelit.iaCore.utils.QuysUtils;
import co.ohelit.iaCore.utils.WebClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class ApiStep implements Steps {

    private final WebClientService webClientService;
    private final QuysUtils quysUtils;

    @Autowired
    public ApiStep (WebClientService webClientService, QuysUtils quysUtils){
        this.webClientService = webClientService;
        this.quysUtils = quysUtils;
    }

    @Override
    public void ejecutar(Map<String, Object> step, String origin) {
        System.out.println("DESDE API STEP: " + step);

        /*Este paso debe extraer información del step para hacer peticiones a APIS exteneras
        sea quysqua u otra

        A continuación se explican todos los atributos de un paso:
        name: NOTES_CONSULT
        type: API
        variableNumber: 4
        parameters:
          method: GET
          apiLink: "https://quysqua"
          filter:
            filterName: id
            localVariableFilter: 1
            filterId: ""
        stepNumber: 2
        nextStep: 3
        */


        Integer variableNumber = (Integer) step.get("variableNumber");
        Integer nextStep = (Integer) step.get("nextStep");

        Map<String, Object> parameters = (Map<String, Object>) step.get("parameters");
        String method = (String) parameters.get("method");
        String apiLink = (String) parameters.get("apiLink");

        Map<String, Object> filter = (Map<String, Object>) parameters.get("filter");
        String filterName = (String) filter.get("filterName");
        String filterId = (String) filter.get("filterId");
        Integer localFilter = (Integer)filter.get("localVariableFilter");

        System.out.println(variableNumber +" "+ nextStep +" "+ method +" "+ apiLink +" "+ filter);


        Mono<ResponseEntity<String>> responseMono = webClientService.makeRequest(apiLink, webClientService.buildMethod(method), null,
                quysUtils.getQuysToken(),
                "application/json", buildParams(filterName, filterId, localFilter), null );

        System.out.println(responseMono.block().getBody());


        if(variableNumber != null){
            System.out.println("Se supone que tengo que guardar la respuesta del usuario");
        } else {
            System.out.println("No debo guardar nadota");
        }
    }

    public Map<String, String> buildParams (String filterName, String filterId, Integer localFilter){
        if (localFilter!=null){
            return webClientService.buildFilterParams(filterName, localFilter.toString());
        } else {
            return webClientService.buildFilterParams(filterName, filterId);
        }
    }

}
