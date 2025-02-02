package com.practice.demo.dto.movie.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MovieResponse(
        Long movieId,
        String title,
        String director,
        LocalDate releaseDate,
        String genre,
        Float rating,
        String thumbnail
) {
    public static MovieResponse of(Long movieId, String title, String director, LocalDate releaseDate, String genre, Float rating, String thumbnail) {
        return MovieResponse.builder()
                .movieId(movieId)
                .title(title)
                .director(director)
                .releaseDate(releaseDate)
                .genre(genre)
                .rating(rating)
                .thumbnail(thumbnail)
                .build();
    }
}
