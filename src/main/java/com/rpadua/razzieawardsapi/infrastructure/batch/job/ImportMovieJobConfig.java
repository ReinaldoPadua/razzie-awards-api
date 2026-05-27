package com.rpadua.razzieawardsapi.infrastructure.batch.job;

import com.rpadua.razzieawardsapi.infrastructure.batch.dto.MovieCsvLine;
import com.rpadua.razzieawardsapi.infrastructure.batch.processor.MovieProcessor;
import com.rpadua.razzieawardsapi.infrastructure.persistence.entity.MovieEntity;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.database.JpaItemWriter;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ImportMovieJobConfig {

    private final JobRepository jobRepository;

    private final PlatformTransactionManager transactionManager;

    private final FlatFileItemReader<MovieCsvLine> reader;

    private final MovieProcessor processor;

    private final JpaItemWriter<MovieEntity> writer;

    public ImportMovieJobConfig(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            FlatFileItemReader<MovieCsvLine> reader,
            MovieProcessor processor,
            JpaItemWriter<MovieEntity> writer
    ) {

        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.reader = reader;
        this.processor = processor;
        this.writer = writer;
    }

    @Bean
    public Step importMovieStep() {

        return new StepBuilder(
                "importMovieStep",
                jobRepository
        )
                .<MovieCsvLine, MovieEntity>chunk(10)
                .transactionManager(transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job importMovieJob() {

        return new JobBuilder(
                "importMovieJob",
                jobRepository
        )
                .incrementer(new RunIdIncrementer())
                .start(importMovieStep())
                .build();
    }
}