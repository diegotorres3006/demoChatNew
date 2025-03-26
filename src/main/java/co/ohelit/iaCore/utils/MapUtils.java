package co.ohelit.iaCore.utils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MapUtils {

    public static <T> Optional<T> getNestedValue(Map<String, Object> map, String... keys) {
        Object value = map;
        for (String key : keys) {
            if (!(value instanceof Map)) {
                return Optional.empty();
            }
            value = ((Map<String, Object>) value).get(key);
        }
        return Optional.ofNullable((T) value);
    }

    @SuppressWarnings("unchecked")
    public static <T> Optional<T> getFirstFromList(Map<String, Object> map, String key) {
        return Optional.ofNullable(map.get(key))
                .filter(List.class::isInstance)
                .map(List.class::cast)
                .filter(list -> !list.isEmpty())
                .map(list -> (T) list.get(0));
    }





}
