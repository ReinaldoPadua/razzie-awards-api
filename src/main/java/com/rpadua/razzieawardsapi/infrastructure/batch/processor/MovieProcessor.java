package com.rpadua.razzieawardsapi.infrastructure.batch.processor;

import com.rpadua.razzieawardsapi.infrastructure.batch.dto.MovieCsvLine;
import com.rpadua.razzieawardsapi.infrastructure.persistence.entity.MovieEntity;
import com.rpadua.razzieawardsapi.presentation.controller.ProducerAwardController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class MovieProcessor
        implements ItemProcessor<
                MovieCsvLine,
                MovieEntity> {

    private static final Logger log =
            LoggerFactory.getLogger(MovieProcessor.class);

    @Override
    public MovieEntity process(
            MovieCsvLine item
    ) {
        log.info("Import item: {}", item.toString());
        return new MovieEntity(
                item.year(),
                item.title(),
                item.studios(),
                item.producers(),
                "yes".equalsIgnoreCase(
                        item.winner())
        );
    }
}
