package co.ohelit.iaCore.application.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class YamlService {




    // Metodo para pasar de yaml a Json
    public List<Map<String, Object>> yamlToJson(String yamlString) {
        try{
            ObjectMapper yamlMapper  = new ObjectMapper(new YAMLFactory());

            // Convertir YAML a un Objecto
            Object yamlObject = yamlMapper.readValue(yamlString, Object.class);

            // Convertir el YAML a un List de Map
            List<Map<String, Object>> yamlList = yamlMapper.readValue(yamlString, List.class);

            return yamlList;
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

}
