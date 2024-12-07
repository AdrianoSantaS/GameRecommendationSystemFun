package com.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class ApiConfigRawg {

	@Value("${external.api.key.rawg}")
	private String apiKey;

}
