package com.rpadua.razzieawardsapi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpadua.razzieawardsapi.application.dto.AwardIntervalResponse;
import com.rpadua.razzieawardsapi.application.usecase.GetProducerAwardIntervalsUseCase;
import com.rpadua.razzieawardsapi.domain.model.Movie;
import com.rpadua.razzieawardsapi.domain.repository.MovieRepository;
import com.rpadua.razzieawardsapi.infrastructure.persistence.repository.MovieJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator; // ← substituiu JobLauncher
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBatchTest
class ProducerAwardIntegrationTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }
    }

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private MovieJpaRepository jpaRepository;
    @Autowired private MovieRepository movieRepository;
    @Autowired private JobOperator jobOperator;
    @Autowired private Job importMovieJob;
    @Autowired private DataSource dataSource;
    @Autowired private jakarta.persistence.EntityManagerFactory entityManagerFactory;
    @Autowired private JobRepository jobRepository;
    @Autowired private GetProducerAwardIntervalsUseCase getAwardIntervalUseCase;

    @Test
    void shouldLoadExactlyTheSameDataFromCsvIntoRepository() throws Exception {

        ClassPathResource resource = new ClassPathResource("Movielist.csv");

        List<String> csvLines = Files.readAllLines(
                resource.getFile().toPath()
        );

        List<Movie> repositoryMovies = movieRepository.findAllMovies();

        List<String> expected = csvLines.stream()
                .skip(1)
                .map(line -> line.replace("\"", "").trim())
                .sorted()
                .toList();

        List<String> actual = repositoryMovies.stream()
                .map(movie ->
                        movie.year() + ";" +
                                movie.title() + ";" +
                                movie.studios() + ";" +
                                movie.producers() + ";" +
                                (movie.winner() ? "yes" : "")
                )
                .sorted()
                .toList();

        assertThat(actual)
                .containsExactlyElementsOf(expected);
    }

    @Test
    void apiResultShouldBeStableAfterDbWipeAndBatchReload() throws Exception {

        AwardIntervalResponse response = callApi();
        List<Movie> originalWinners = getFromRepository();

        assertThat(response.min()).isNotEmpty();
        assertThat(response.max()).isNotEmpty();
        assertThat(originalWinners).isNotEmpty();

        AwardIntervalResponse expected =
                getAwardIntervalUseCase.execute();

        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private AwardIntervalResponse callApi() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/producers/awards/intervals"))
                .andExpect(status().isOk())
                .andReturn();
        return objectMapper.readValue(
                result.getResponse().getContentAsString(),
                AwardIntervalResponse.class);
    }

    private List<Movie> getFromRepository() {
        return movieRepository.findAllWinningMovies().stream()
                .sorted(Comparator.comparing(Movie::year).thenComparing(Movie::title))
                .toList();
    }
}