package com.practice.demo.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movie extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String director;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    private String genre;

    private Float rating;

    private String description;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<MoviePoster> moviePosterList = new ArrayList<>();

    public static Movie create(String title, String director, LocalDate releaseDate, String genre, Float rating, String description) {
        return Movie.builder()
                .title(title)
                .director(director)
                .releaseDate(releaseDate)
                .genre(genre)
                .rating(rating)
                .description(description)
                .build();
    }

    public void update(String title, String director, LocalDate releaseDate, String genre, Float rating, String description) {
        if (title != null) {
            this.title = title;
        }
        if (director != null) {
            this.director = director;
        }
        if (releaseDate != null) {
            this.releaseDate = releaseDate;
        }
        if (genre != null) {
            this.genre = genre;
        }
        if (rating != null) {
            this.rating = rating;
        }
        if (description != null) {
            this.description = description;
        }
    }
}
