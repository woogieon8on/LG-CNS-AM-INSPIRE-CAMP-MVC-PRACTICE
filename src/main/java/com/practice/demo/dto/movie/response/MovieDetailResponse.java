package com.practice.demo.dto.movie.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MovieDetailResponse(
        Long movieId,
        String title,
        String director,
        LocalDate releaseDate,
        String genre,
        Float rating,
        String description,
        String thumbnail
) {
    public static MovieDetailResponse of(Long movieId, String title, String director, LocalDate releaseDate, String genre, Float rating, String description, String thumbnail) {
        return MovieDetailResponse.builder()
                .movieId(movieId)
                .title(title)
                .director(director)
                .releaseDate(releaseDate)
                .genre(genre)
                .rating(rating)
                .description(description)
                .thumbnail(thumbnail)
                .build();
    }
}
