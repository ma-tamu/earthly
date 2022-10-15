package jp.co.project.planets.earthly.auth.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

/**
 * convert helper
 */
@Component
public class ConvertHelper {

    private final ObjectMapper objectMapper;

    /**
     * new instances convert helper
     *
     * @param objectMapper
     *         object mapper
     */
    public ConvertHelper(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * convert object into json
     *
     * @param obj
     *         Object
     * @return json
     */
    public String convertObjectIntoJson(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException("object to json convert failed.", e);
        }
    }

    /**
     * convert json into object
     *
     * @param json
     *         json
     * @param clazz
     *         convert object class
     * @return Object
     */
    public <T> T convertJsonIntoObject(final String json, final Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException("json to object canvert failed.", e);
        }
    }
}
