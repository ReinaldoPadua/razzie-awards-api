package com.rpadua.razzieawardsapi.infrastructure.batch.reader;

import com.rpadua.razzieawardsapi.infrastructure.batch.dto.MovieCsvLine;
import org.springframework.batch.infrastructure.item.file.LineMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.infrastructure.item.file.separator.DefaultRecordSeparatorPolicy;
import org.springframework.batch.infrastructure.item.file.transform.DelimitedLineTokenizer;

@Configuration
public class CsvReaderConfig {

    @Value("${app.csv.file-name}")
    private String fileName;

    @Bean
    public FlatFileItemReader<MovieCsvLine> reader() {

        FlatFileItemReader<MovieCsvLine> reader =
                new FlatFileItemReader<>(movieLineMapper());

        reader.setResource(
                new ClassPathResource(fileName));

        reader.setLinesToSkip(1);

        reader.setRecordSeparatorPolicy(
                new DefaultRecordSeparatorPolicy());

        return reader;
    }

    @Bean
    public LineMapper<MovieCsvLine> movieLineMapper() {

        DelimitedLineTokenizer tokenizer =
                new DelimitedLineTokenizer();

        tokenizer.setDelimiter(";");

        tokenizer.setNames(
                "year",
                "title",
                "studios",
                "producers",
                "winner"
        );

        DefaultLineMapper<MovieCsvLine> lineMapper =
                new DefaultLineMapper<>();

        lineMapper.setLineTokenizer(tokenizer);

        lineMapper.setFieldSetMapper(fieldSet ->
                new MovieCsvLine(
                        fieldSet.readInt("year"),
                        fieldSet.readString("title"),
                        fieldSet.readString("studios"),
                        fieldSet.readString("producers"),
                        fieldSet.readString("winner")
                )
        );

        return lineMapper;
    }
}
