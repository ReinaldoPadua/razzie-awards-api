package com.rpadua.razzieawardsapi.infrastructure.persistence.mapper;

import com.rpadua.razzieawardsapi.domain.model.Movie;
import com.rpadua.razzieawardsapi.infrastructure.persistence.entity.MovieEntity;
import org.springframework.stereotype.Component;

@Component
public class MovieEntityMapper {

    public Movie toDomain(
            MovieEntity entity
    ) {

        return new Movie(
                entity.getId(),
                entity.getReleaseYear(),
                entity.getTitle(),
                entity.getStudios(),
                entity.getProducers(),
                entity.getWinner()
        );
    }
}