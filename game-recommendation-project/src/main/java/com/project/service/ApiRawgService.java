package com.project.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.config.ApiConfigRawg;
import com.project.model.details.RawgResponse;

@Service
public class ApiRawgService {

    @Autowired
    private ApiConfigRawg apiConfigRawg;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RawgResponse getDataFromApi() {
        String url = "https://api.rawg.io/api/games?key=" + apiConfigRawg.getApiKey();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        try {
            return objectMapper.readValue(response.getBody(), RawgResponse.class);
        } catch (IOException e) {
            throw new RuntimeException("Error process request API", e);
        }
    }
}

