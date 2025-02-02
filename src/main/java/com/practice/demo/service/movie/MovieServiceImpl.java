package com.practice.demo.service.movie;

import com.practice.demo.domain.Movie;
import com.practice.demo.domain.MoviePoster;
import com.practice.demo.dto.movie.request.MovieRequest;
import com.practice.demo.dto.movie.response.MovieDetailResponse;
import com.practice.demo.dto.movie.response.MovieResponse;
import com.practice.demo.dto.movieposter.MoviePosterDto;
import com.practice.demo.global.FileUtils;
import com.practice.demo.repository.MoviePosterRepository;
import com.practice.demo.repository.MovieRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MoviePosterRepository moviePosterRepository;
    private final FileUtils fileUtils;

    @Override
    public void saveMovie(MovieRequest movieRequest, MultipartHttpServletRequest request) {
        Movie movie = Movie.create(
                movieRequest.title(),
                movieRequest.director(),
                movieRequest.releaseDate(),
                movieRequest.genre(),
                movieRequest.rating(),
                movieRequest.description()
        );

        movieRepository.save(movie);

        // 영화 포스터 이미지 파일 받아서 저장
        try {
            // 첨부 파일을 디스크에 저장하고, 첨부 파일 정보를 반환
            List<MoviePosterDto> fileInfoList = fileUtils.parseFileInfo(movie, request);

            // 첨부 파일 정보를 DB에 저장
            fileInfoList
                    .forEach(f -> {
                        MoviePoster moviePoster = MoviePoster.create(f.posterUrl(), f.storedPath(), f.movie());
                        moviePosterRepository.save(moviePoster);
                    });

        } catch(Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public List<MovieResponse> showMovieList() {
        List<Movie> movies = movieRepository.findAll();

        List<MovieResponse> movieResponses = movies.stream()
                .map(m -> MovieResponse.of(
                        m.getId(),
                        m.getTitle(),
                        m.getDirector(),
                        m.getReleaseDate(),
                        m.getGenre(),
                        m.getRating(),
                        // 영화 포스터 썸네일 포함
                        m.getMoviePosterList().stream().findFirst().orElse(null).getStoredPath()
                ))
                .toList();

        return movieResponses;
    }

    @Override
    public MovieDetailResponse showMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException());

        // 영화 포스터 포함
        MoviePoster moviePoster = moviePosterRepository.findFirstByMovie_Id(movieId)
                .orElseThrow(() -> new RuntimeException());

        MovieDetailResponse movieDetailResponse = MovieDetailResponse.of(
                movie.getId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getReleaseDate(),
                movie.getGenre(),
                movie.getRating(),
                movie.getDescription(),
                moviePoster.getStoredPath()
        );

        return movieDetailResponse;
    }

    @Override
    public void updateMovie(Long movieId, MovieRequest movieRequest) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException());

        movie.update(
                movieRequest.title(),
                movieRequest.director(),
                movieRequest.releaseDate(),
                movieRequest.genre(),
                movieRequest.rating(),
                movieRequest.description()
        );
    }

    @Override
    public void deleteMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException());

        movieRepository.delete(movie);
    }

    @Override
    public MoviePosterDto showMoviePoster(Long movieId) {
        MoviePoster moviePoster = moviePosterRepository.findFirstByMovie_Id(movieId)
                .orElseThrow(() -> new RuntimeException());

        return MoviePosterDto.of(
                moviePoster.getMovie(),
                moviePoster.getPosterUrl(),
                moviePoster.getStoredPath()
        );
    }
}
