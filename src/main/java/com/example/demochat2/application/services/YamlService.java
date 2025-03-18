package com.example.demochat2.application.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.util.List;
import java.util.Map;

public class YamlService {
    // Metodo para pasar de yaml a Json
    public List<Map<String, Object>> yamlToJson(String yamlString) throws JsonProcessingException {
        ObjectMapper yamlMapper  = new ObjectMapper(new YAMLFactory());

        // Convertir YAML a un Objecto
        Object yamlObject = yamlMapper.readValue(yamlString, Object.class);

        // Convertir el YAML a un List de Map
        List<Map<String, Object>> yamlList = yamlMapper.readValue(yamlString, List.class);

        // Convertir el objeto a JSON en formato String
        //String jsonString = jsonMapper.writeValueAsString(yamlObject);

        return yamlList;
    }

    // Metodo para encontrar el paso por número de step
    public Map<String, Object> findStepByNumber(List<Map<String, Object>> yamlList, int targetStepNumber) {

        for (Map<String, Object> item : yamlList) {
            // Acceder al mapa 'steps' y verificar el 'stepNumber'
            Map<String, Object> steps = (Map<String, Object>) item.get("steps");
            Integer stepNumber = (Integer) steps.get("stepNumber");
            if (stepNumber != null && stepNumber == targetStepNumber) {
                return item;
            }
        }
        return null;
    }
}
