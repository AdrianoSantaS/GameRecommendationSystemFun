package com.project.model.details;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameDetails {

    private Long id;
    private String name;
    private String released;

    @JsonProperty("background_image")
    private String backgroundImage;

    private double rating;

    @JsonProperty("ratings_count")
    private int ratingsCount;

    private int playtime;
    private List<PlatformDetails> platforms;
    private List<Genre> genres;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PlatformDetails {
        private Platform platform;
        private String released_at;
        private Requirements requirements_en;

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Platform {
            private Long id;
            private String name;
            private String slug;
            private String image;

            @JsonProperty("year_start")
            private Integer yearStart;

            @JsonProperty("year_end")
            private Integer yearEnd;

            @JsonProperty("games_count")
            private Integer gamesCount;

            @JsonProperty("image_background")
            private String imageBackground;
        }

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Requirements {
            private String minimum;
            private String recommended;
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Genre {
        private Long id;
        private String name;
        private String slug;

        @JsonProperty("games_count")
        private Integer games_count;

        @JsonProperty("image_background")
        private String image_background;
    }
}
