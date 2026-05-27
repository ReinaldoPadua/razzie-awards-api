package com.rpadua.razzieawardsapi.infrastructure.batch.writer;


import com.rpadua.razzieawardsapi.infrastructure.persistence.entity.MovieEntity;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.infrastructure.item.database.JpaItemWriter;
import org.springframework.batch.infrastructure.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CsvWriterConfig {

    @Bean
    public JpaItemWriter<MovieEntity> writer(
            EntityManagerFactory entityManagerFactory
    ) {
        return new JpaItemWriterBuilder<MovieEntity>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
