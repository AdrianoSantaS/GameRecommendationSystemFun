package com.project.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.model.details.FreeToGameResponse;

@Service
public class ApiFreeToGamesService {

	private final RestTemplate restTemplate = new RestTemplate();
	private final ObjectMapper objectMapper = new ObjectMapper();

	public FreeToGameResponse[] getDataFromApi() {
		String url = "https://www.freetogame.com/api/games";

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

		try {
			return objectMapper.readValue(response.getBody(), FreeToGameResponse[].class);
		} catch (IOException e) {
			throw new RuntimeException("Error processing request API", e);
		}
	}
}
