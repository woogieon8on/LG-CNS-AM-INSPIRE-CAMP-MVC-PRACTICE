package com.practice.demo.service.movie;

import com.practice.demo.dto.movie.request.MovieRequest;
import com.practice.demo.dto.movie.response.MovieDetailResponse;
import com.practice.demo.dto.movie.response.MovieResponse;
import com.practice.demo.dto.movieposter.MoviePosterDto;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface MovieService {
    void saveMovie(MovieRequest movieRequest, MultipartHttpServletRequest request);
    List<MovieResponse> showMovieList();
    MovieDetailResponse showMovie(Long movieId);
    void updateMovie(Long movieId, MovieRequest movieRequest);
    void deleteMovie(Long movieId);

    MoviePosterDto showMoviePoster(Long movieId);
}
