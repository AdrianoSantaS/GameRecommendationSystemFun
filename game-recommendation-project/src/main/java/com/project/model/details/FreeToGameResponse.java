package com.project.model.details;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class FreeToGameResponse {

	private int id;
	private String title;
	private String thumbnail;
	@JsonProperty("short_description")
	private String shortDescription;
	@JsonProperty("game_url")
	private String gameUrl;
	private String genre;
	private String platform;
	private String publisher;
	private String developer;
	@JsonProperty("release_date")
	private String releaseDate;
	@JsonProperty("freetogame_profile_url")
	private String freeToGameProfileUrl;
}
