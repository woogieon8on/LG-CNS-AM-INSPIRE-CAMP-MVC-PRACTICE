package com.practice.demo.controller;

import com.practice.demo.dto.movie.request.MovieRequest;
import com.practice.demo.dto.movie.response.MovieDetailResponse;
import com.practice.demo.dto.movie.response.MovieResponse;
import com.practice.demo.dto.movieposter.MoviePosterDto;
import com.practice.demo.service.movie.MovieService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    // 영화 리스트 조회
    @GetMapping("/movie/openMovieList.do")
    public ModelAndView openMovieList() throws Exception {
        ModelAndView modelAndView = new ModelAndView("/movie/movieList");

        List<MovieResponse> movieResponses = movieService.showMovieList();
        modelAndView.addObject("movieResponses", movieResponses);

        return modelAndView;
    }

    // 영화 생성 화면 요청
    @GetMapping("/movie/openMovieWrite.do")
    public String openMovieWrite() throws Exception {
        return "/movie/movieWrite";
    }

    // 영화 생성 요청
    @PostMapping("/movie/insertMovie.do")
    public String insertMovie(MovieRequest movieRequest, MultipartHttpServletRequest request) throws Exception {
        movieService.saveMovie(movieRequest, request);
        return "redirect:/movie/openMovieList.do";
    }

    // 상세 조회 요청
    @GetMapping("/movie/openMovieDetail.do")
    public ModelAndView openMovieDetail(@RequestParam("movieId") Long movieId) throws Exception {
        MovieDetailResponse movieDetailResponse = movieService.showMovie(movieId);

        ModelAndView modelAndView = new ModelAndView("/movie/movieDetail");
        modelAndView.addObject("movieDetailResponse", movieDetailResponse);
        return modelAndView;
    }

    // 수정 요청
    @PostMapping("/movie/updateMovie.do")
    public String updateMovie(@RequestParam("movieId") Long movieId, MovieRequest movieRequest) throws Exception {
        movieService.updateMovie(movieId, movieRequest);
        return "redirect:/movie/openMovieList.do";
    }

    // 삭제 요청
    @PostMapping("/movie/deleteMovie.do")
    public String deleteMovie(@RequestParam("movieId") Long movieId) throws Exception {
        movieService.deleteMovie(movieId);
        return "redirect:/movie/openMovieList.do";
    }

    // 파일 다운로드 요청
    @GetMapping("/movie/downloadMoviePoster.do")
    public void downloadBoardFile(@RequestParam("movieId") Long movieId, HttpServletResponse response) throws Exception {
        MoviePosterDto moviePosterDto = movieService.showMoviePoster(movieId);
        if (ObjectUtils.isEmpty(moviePosterDto)) {
            return;
        }

        Path path = Paths.get(moviePosterDto.storedPath());
        byte[] file = Files.readAllBytes(path);

        response.setContentType("application/octet-stream");
        response.setContentLength(file.length);
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(moviePosterDto.posterUrl(), "UTF-8") + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.getOutputStream().write(file);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}
