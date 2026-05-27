package com.rpadua.razzieawardsapi.application.usecase;

import com.rpadua.razzieawardsapi.application.dto.AwardIntervalResponse;
import com.rpadua.razzieawardsapi.application.dto.ProducerIntervalDto;
import com.rpadua.razzieawardsapi.domain.model.Movie;
import com.rpadua.razzieawardsapi.domain.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetProducerAwardIntervalsUseCase {
    private final MovieRepository movieRepository;

    public GetProducerAwardIntervalsUseCase(
            MovieRepository movieRepository
    ) {
        this.movieRepository = movieRepository;
    }

    public AwardIntervalResponse execute() {

        List<Movie> winners =
                movieRepository.findAllWinningMovies();

        Map<String, List<Integer>> producerWins =
                new HashMap<>();

        for (Movie movie : winners) {

            extractProducers(movie.producers())
                    .forEach(producer ->

                            producerWins
                                    .computeIfAbsent(
                                            producer,
                                            p -> new ArrayList<>())
                                    .add(movie.year())
                    );
        }

        List<ProducerIntervalDto> intervals =
                calculateIntervals(producerWins);

        int min = intervals.stream()
                .mapToInt(ProducerIntervalDto::interval)
                .min()
                .orElse(0);

        int max = intervals.stream()
                .mapToInt(ProducerIntervalDto::interval)
                .max()
                .orElse(0);

        return new AwardIntervalResponse(

                intervals.stream()
                        .filter(i -> i.interval() == min)
                        .toList(),

                intervals.stream()
                        .filter(i -> i.interval() == max)
                        .toList()
        );
    }

    private List<String> extractProducers(
            String raw
    ) {

        return Arrays.stream(
                        raw.replace(" and ", ",")
                                .split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .toList();
    }

    private List<ProducerIntervalDto>
    calculateIntervals(
            Map<String, List<Integer>> producerWins
    ) {

        List<ProducerIntervalDto> result =
                new ArrayList<>();

        for (var entry : producerWins.entrySet()) {

            List<Integer> years = entry.getValue();

            if (years.size() < 2) {
                continue;
            }

            Collections.sort(years);

            for (int i = 1; i < years.size(); i++) {

                int previous = years.get(i - 1);

                int current = years.get(i);

                result.add(
                        new ProducerIntervalDto(
                                entry.getKey(),
                                current - previous,
                                previous,
                                current
                        )
                );
            }
        }

        return result;
    }
}
