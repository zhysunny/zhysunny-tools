package com.zhysunny.common.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author zhysunny
 * @date 2020/8/25 22:42
 */
public class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String toJson(Object obj) throws JsonProcessingException {
        return MAPPER.writeValueAsString(obj);
    }

    public static String toJsonSilent(Object obj) {
        try {
            return toJson(obj);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static <T> T parse(String json, Class<T> clz) throws IOException {
        return MAPPER.readValue(json.getBytes(StandardCharsets.UTF_8), clz);
    }

    public static <T> T parseSilent(String json, Class<T> clz) {
        try {
            return parse(json, clz);
        } catch (IOException e) {
            return null;
        }
    }

}
