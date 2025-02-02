package com.practice.demo.dto.movieposter;

import com.practice.demo.domain.Movie;
import lombok.Builder;

@Builder
public record MoviePosterDto(
        Movie movie,
        String posterUrl,
        String storedPath
) {
    public static MoviePosterDto of(Movie movie, String posterUrl, String storedPath) {
        return MoviePosterDto.builder()
                .movie(movie)
                .posterUrl(posterUrl)
                .storedPath(storedPath)
                .build();
    }
}
