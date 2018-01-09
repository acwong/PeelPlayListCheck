package com.acwong.peelplaylistcheck.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class FileLoader {
    /**
     * Load video contents data with external content file override.
     *
     * @param resourceFileName Peel Content File in JSON format
     * @param jsonClass        Output JSON object class type
     * @return Parsed JSON object. {@code null} on file read error.
     */
    public <T> T loadFromJson(String resourceFileName, Class<T> jsonClass) {
        InputStream inputStream = getClass().getResourceAsStream('/' + resourceFileName);
        T object = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            object = mapper.readValue(inputStream, jsonClass);
        } catch (IOException e) {
        }
        return object;
    }
}
