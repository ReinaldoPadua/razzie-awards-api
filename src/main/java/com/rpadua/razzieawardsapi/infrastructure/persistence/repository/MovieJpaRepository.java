package com.rpadua.razzieawardsapi.infrastructure.persistence.repository;

import com.rpadua.razzieawardsapi.infrastructure.persistence.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieJpaRepository
        extends JpaRepository<MovieEntity, Long> {

    List<MovieEntity> findByWinnerTrue();
}