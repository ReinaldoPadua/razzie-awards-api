package com.rpadua.razzieawardsapi.application.usecase;

import com.rpadua.razzieawardsapi.application.dto.AwardIntervalResponse;
import com.rpadua.razzieawardsapi.application.dto.ProducerIntervalDto;
import com.rpadua.razzieawardsapi.domain.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

@Service
public class GetProducerAwardIntervalsUseCase {
    private final MovieRepository movieRepository;

    public GetProducerAwardIntervalsUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public AwardIntervalResponse execute() {
        var intervals = buildIntervals();
        var stats = intervals.stream()
                .mapToInt(ProducerIntervalDto::interval)
                .summaryStatistics();

        return partitionByMinMax(intervals, stats.getMin(), stats.getMax());
    }

    private List<ProducerIntervalDto> buildIntervals() {
        return movieRepository.findAllWinningMovies()
                .stream()
                .flatMap(movie -> extractProducers(movie.producers())
                        .stream()
                        .map(producer -> Map.entry(producer, movie.year())))
                .collect(groupingBy(Map.Entry::getKey, mapping(Map.Entry::getValue, toList())))
                .entrySet()
                .stream()
                .flatMap(e -> toIntervals(e.getKey(), e.getValue()))
                .toList();
    }

    private Stream<ProducerIntervalDto> toIntervals(String producer, List<Integer> years) {
        var sorted = years.stream().sorted().toList();
        return IntStream.range(1, sorted.size())
                .mapToObj(i -> new ProducerIntervalDto(
                        producer,
                        sorted.get(i) - sorted.get(i - 1),
                        sorted.get(i - 1),
                        sorted.get(i)
                ));
    }

    private AwardIntervalResponse partitionByMinMax(List<ProducerIntervalDto> intervals, int min, int max) {
        var grouped = intervals.stream()
                .filter(i -> i.interval() == min || i.interval() == max)
                .collect(partitioningBy(i -> i.interval() == min));

        return new AwardIntervalResponse(grouped.get(true), grouped.get(false));
    }

    private List<String> extractProducers(String raw) {
        return Arrays.stream(raw.replace(" and ", ",").split(","))
                .map(String::trim)
                .filter(Predicate.not(String::isBlank))
                .toList();
    }
}