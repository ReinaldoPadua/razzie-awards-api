package com.rpadua.razzieawardsapi.domain.repository;

import com.rpadua.razzieawardsapi.domain.model.Movie;

import java.util.List;

public interface MovieRepository {
    List<Movie> findAllWinningMovies();
}
