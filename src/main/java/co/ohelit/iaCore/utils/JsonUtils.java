package co.ohelit.iaCore.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Component
public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir el objeto a JSON", e);
        }
    }

    public static <T> Optional<T> getNestedValue(Map<String, Object> map, String... keys) {
        Object value = map;
        for (String key : keys) {
            if (value instanceof Map) {
                value = ((Map<?, ?>) value).get(key);
            } else if (value instanceof List<?> list && !list.isEmpty() && list.get(0) instanceof Map) {
                value = list.get(0);
            } else {
                return Optional.empty();
            }
        }
        return Optional.ofNullable((T) value);
    }
}
