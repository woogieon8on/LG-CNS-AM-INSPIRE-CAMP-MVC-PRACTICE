package com.practice.demo.repository;

import com.practice.demo.domain.MoviePoster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MoviePosterRepository extends JpaRepository<MoviePoster, Long> {
    Optional<MoviePoster> findFirstByMovie_Id(Long movieId);
}
