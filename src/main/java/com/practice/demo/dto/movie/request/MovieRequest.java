package com.practice.demo.dto.movie.request;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MovieRequest(
    String title,
    String director,
    LocalDate releaseDate,
    String genre,
    Float rating,
    String description
) {
}
