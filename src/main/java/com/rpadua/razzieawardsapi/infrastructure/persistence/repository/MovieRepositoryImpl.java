package com.rpadua.razzieawardsapi.infrastructure.persistence.repository;

import com.rpadua.razzieawardsapi.domain.model.Movie;
import com.rpadua.razzieawardsapi.domain.repository.MovieRepository;
import com.rpadua.razzieawardsapi.infrastructure.persistence.mapper.MovieEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieRepositoryImpl
        implements MovieRepository {

    private final MovieJpaRepository repository;

    private final MovieEntityMapper mapper;

    public MovieRepositoryImpl(
            MovieJpaRepository repository,
            MovieEntityMapper mapper
    ) {

        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<Movie> findAllWinningMovies() {

        return repository.findByWinnerTrue()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
