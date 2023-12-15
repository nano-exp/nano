package nano.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fetch.support.UnsafeUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public abstract class Json {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static @NotNull String stringify(@NotNull Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            UnsafeUtils.getUnsafe().throwException(ex);
            return "";
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> parseList(String jsonString) {
        try {
            return (List<T>) objectMapper.readValue(jsonString, List.class);
        } catch (JsonProcessingException ex) {
            UnsafeUtils.getUnsafe().throwException(ex);
            return List.of();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Map<String, T> parseMap(String jsonString) {
        try {
            return (Map<String, T>) objectMapper.readValue(jsonString, Map.class);
        } catch (JsonProcessingException ex) {
            UnsafeUtils.getUnsafe().throwException(ex);
            return Map.of();
        }
    }
}
