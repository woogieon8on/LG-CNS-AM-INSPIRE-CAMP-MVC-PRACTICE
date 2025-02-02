package com.practice.demo.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movie_poster")
public class MoviePoster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "poster_url")
    private String posterUrl;

    @Column(name = "stored_path")
    private String storedPath;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public static MoviePoster create(String posterUrl, String storedPath, Movie movie) {
        return MoviePoster.builder()
                .posterUrl(posterUrl)
                .storedPath(storedPath)
                .movie(movie)
                .build();
    }
}
